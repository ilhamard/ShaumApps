package dev.nocturnbinary.dzikirkita.features.prayerschedule.data

import dev.nocturnbinary.dzikirkita.network.NetworkResult

interface PrayerScheduleRepository {
    suspend fun getPrayerSchedule(
        date: String,
        city: String,
        country: String,
        method: Int,
    ): NetworkResult<PrayerScheduleApiModel>
}