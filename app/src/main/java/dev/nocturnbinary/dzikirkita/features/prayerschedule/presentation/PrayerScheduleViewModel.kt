package dev.nocturnbinary.dzikirkita.features.prayerschedule.presentation

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nocturnbinary.dzikirkita.features.prayerschedule.data.Timings
import dev.nocturnbinary.dzikirkita.features.prayerschedule.domain.GetPrayerScheduleUseCase
import dev.nocturnbinary.dzikirkita.features.prayerschedule.domain.NextPrayerScheduleUseCase
import dev.nocturnbinary.dzikirkita.features.prayerschedule.domain.PrevPrayerScheduleUseCase
import dev.nocturnbinary.dzikirkita.features.prayerschedule.domain.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PrayerScheduleViewModel @Inject constructor(
    private val getPrayerScheduleUseCase: GetPrayerScheduleUseCase,
    private val prevPrayerScheduleUseCase: PrevPrayerScheduleUseCase,
    private val nextPrayerScheduleUseCase: NextPrayerScheduleUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrayerScheduleUiState())
    val uiState: StateFlow<PrayerScheduleUiState> = _uiState
    private val calendar = Calendar.getInstance()

    init {
        getPrayerSchedule()
    }

    fun onEvent(event: PrayerScheduleUiEvent) {
        when (event) {
            PrayerScheduleUiEvent.PreviousDate -> {
                prevPrayerSchedule()
            }

            PrayerScheduleUiEvent.NextDate -> {
                nextPrayerSchedule()
            }
        }
    }

    private fun getPrayerSchedule() = viewModelScope.launch {
        when (val result = getPrayerScheduleUseCase.invoke()) {
            is Resource.Success -> {
                val timings = result.result.data.timings
                val dateHijr = result.result.data.date.hijri.day
                val monthHijr = result.result.data.date.hijri.month.en
                val yearHijr = result.result.data.date.hijri.year
                val dateMasehi = result.result.data.date.readable

                _uiState.value = _uiState.value.copy(
                    imsakTime = timings.imsak,
                    subuhTime = timings.fajr,
                    terbitTime = timings.sunrise,
                    dzuhurTime = timings.dhuhr,
                    asharTime = timings.asr,
                    maghribTime = timings.maghrib,
                    isyaTime = timings.isha,
                    dateHijr = dateHijr,
                    monthHijr = monthHijr,
                    yearHijr = yearHijr,
                    dateMasehi = dateMasehi
                )

                updatePrayerTimes(timings)
            }

            is Resource.Error -> {
                _uiState.value =
                    _uiState.value.copy(isLoading = false, errorMessage = result.e.name)
            }
        }
    }

    private fun prevPrayerSchedule() =
        viewModelScope.launch {
            when (val result = prevPrayerScheduleUseCase.invoke(calendar = calendar)) {
                is Resource.Success -> {
                    val timings = result.result.data.timings
                    val dateHijr = result.result.data.date.hijri.day
                    val monthHijr = result.result.data.date.hijri.month.en
                    val yearHijr = result.result.data.date.hijri.year
                    val dateMasehi = result.result.data.date.readable

                    _uiState.value = _uiState.value.copy(
                        imsakTime = timings.imsak,
                        subuhTime = timings.fajr,
                        terbitTime = timings.sunrise,
                        dzuhurTime = timings.dhuhr,
                        asharTime = timings.asr,
                        maghribTime = timings.maghrib,
                        isyaTime = timings.isha,
                        dateHijr = dateHijr,
                        monthHijr = monthHijr,
                        yearHijr = yearHijr,
                        dateMasehi = dateMasehi
                    )
                }

                is Resource.Error -> {
                    _uiState.value =
                        _uiState.value.copy(isLoading = false, errorMessage = result.e.name)
                }
            }
        }

    private fun nextPrayerSchedule() =
        viewModelScope.launch {
            when (val result = nextPrayerScheduleUseCase.invoke(calendar = calendar)) {
                is Resource.Success -> {
                    val timings = result.result.data.timings
                    val dateHijr = result.result.data.date.hijri.day
                    val monthHijr = result.result.data.date.hijri.month.en
                    val yearHijr = result.result.data.date.hijri.year
                    val dateMasehi = result.result.data.date.readable

                    _uiState.value = _uiState.value.copy(
                        imsakTime = timings.imsak,
                        subuhTime = timings.fajr,
                        terbitTime = timings.sunrise,
                        dzuhurTime = timings.dhuhr,
                        asharTime = timings.asr,
                        maghribTime = timings.maghrib,
                        isyaTime = timings.isha,
                        dateHijr = dateHijr,
                        monthHijr = monthHijr,
                        yearHijr = yearHijr,
                        dateMasehi = dateMasehi
                    )
                }

                is Resource.Error -> {
                    _uiState.value =
                        _uiState.value.copy(isLoading = false, errorMessage = result.e.name)
                }
            }
        }

    private fun updatePrayerTimes(timings: Timings) {
        val currentTime =
            getTimeInMillis(SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()), 0)
        val prayerTimes = listOf(
            "Imsak" to getTimeInMillis(timings.imsak, 1),
            "Shubuh" to getTimeInMillis(timings.fajr),
            "Terbit" to getTimeInMillis(timings.sunrise),
            "Dzuhur" to getTimeInMillis(timings.dhuhr),
            "Ashar" to getTimeInMillis(timings.asr),
            "Maghrib" to getTimeInMillis(timings.maghrib),
            "Isya" to getTimeInMillis(timings.isha),
        )

        val nextPrayer = prayerTimes.filter { it.second > currentTime }.minByOrNull { it.second }
        val (nextPrayerLabel, nextPrayerTime) = nextPrayer ?: prayerTimes.first()

        _uiState.value = _uiState.value.copy(
            nextPrayerTime = "$nextPrayerLabel, ${formatTime(nextPrayerTime)}"
        )
        startCountdown(nextPrayerTime - currentTime)
    }

    private fun startCountdown(timeInMillis: Long) {
        var timeDifference = timeInMillis
        if (timeDifference < 0) {
            timeDifference += 24 * 60 * 60 * 1000
        }

        viewModelScope.launch {
            object : CountDownTimer(timeDifference, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val hours = millisUntilFinished / (60 * 60 * 1000)
                    val minutes = millisUntilFinished % (60 * 60 * 1000) / (60 * 1000)
                    val seconds = millisUntilFinished % (60 * 1000) / 1000
                    val countDown = String.format(
                        Locale.getDefault(),
                        "%02d:%02d:%02d",
                        hours,
                        minutes,
                        seconds
                    )

                    _uiState.value = _uiState.value.copy(remainingTime = countDown)
                }

                override fun onFinish() {
                    _uiState.value = _uiState.value.copy(remainingTime = "00:00:00")
                    getPrayerSchedule()
                }
            }.start()
        }
    }

    private fun getTimeInMillis(times: String, extraDays: Int = 0): Long {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = formatter.parse(times) ?: return 0L
        val calendar = Calendar.getInstance().apply {
            time = date
            add(Calendar.DATE, extraDays)
        }
        return calendar.timeInMillis
    }

    private fun formatTime(timeInMillis: Long): String {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(Date(timeInMillis))
    }
}