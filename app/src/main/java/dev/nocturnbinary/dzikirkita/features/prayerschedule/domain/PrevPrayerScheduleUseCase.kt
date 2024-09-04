package dev.nocturnbinary.dzikirkita.features.prayerschedule.domain

import dev.nocturnbinary.dzikirkita.features.prayerschedule.data.PrayerScheduleApiModel
import dev.nocturnbinary.dzikirkita.features.prayerschedule.data.PrayerScheduleRepository
import dev.nocturnbinary.dzikirkita.network.NetworkResult
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class PrevPrayerScheduleUseCase @Inject constructor(
    private val prayerScheduleRepository: PrayerScheduleRepository,
) {

    suspend fun invoke(
        city: String = "Kota Jakarta Selatan",
        country: String = "Indonesia",
        method: Int = 11,
        calendar: Calendar,
    ): Resource<PrayerScheduleApiModel> {

        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val yesterdayDate = calendar.time
        val formatterDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val tgl = formatterDate.format(yesterdayDate)

        return when (val result = prayerScheduleRepository.getPrayerSchedule(
            date = tgl,
            city = city,
            country = country,
            method = method,
        )) {
            is NetworkResult.Error -> result.toResourceError()
            is NetworkResult.Success -> Resource.Success(result.result)
        }
    }
}