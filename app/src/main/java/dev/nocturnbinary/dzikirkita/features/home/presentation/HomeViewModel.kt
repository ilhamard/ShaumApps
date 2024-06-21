package dev.nocturnbinary.dzikirkita.features.home.presentation

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nocturnbinary.dzikirkita.features.hadits.domain.GetRandomHaditsUseCase
import dev.nocturnbinary.dzikirkita.features.hadits.domain.Resource
import dev.nocturnbinary.dzikirkita.features.prayerschedule.data.Timings
import dev.nocturnbinary.dzikirkita.features.prayerschedule.domain.GetPrayerScheduleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val randomHaditsUseCase: GetRandomHaditsUseCase,
    private val getPrayerScheduleUseCase: GetPrayerScheduleUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        getRandomHadits()
        getPrayerSchedule()
    }

    private fun getRandomHadits() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        when (val result = randomHaditsUseCase.invoke()) {
            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(
                    hadits = result.result.contents.id,
                    haditsRiwayat = result.result.name,
                    isLoading = false
                )
            }

            is Resource.Error -> {
                _uiState.value =
                    _uiState.value.copy(isLoading = false, errorMessage = result.e.name)
            }
        }
    }

    private fun getPrayerSchedule() = viewModelScope.launch {
        when (val result = getPrayerScheduleUseCase.invoke()) {
            is dev.nocturnbinary.dzikirkita.features.prayerschedule.domain.Resource.Success -> {
                val timings = result.result.data.timings
                updatePrayerTimes(timings)
            }

            is dev.nocturnbinary.dzikirkita.features.prayerschedule.domain.Resource.Error -> {
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