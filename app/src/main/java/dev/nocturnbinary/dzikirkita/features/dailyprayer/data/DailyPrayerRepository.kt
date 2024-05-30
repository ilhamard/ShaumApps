package dev.nocturnbinary.dzikirkita.features.dailyprayer.data

import dev.nocturnbinary.dzikirkita.network.NetworkResult

interface DailyPrayerRepository {
    suspend fun getDailyPrayer(): NetworkResult<List<DailyPrayerApiModel>>
    suspend fun getPrayerById(id: String): NetworkResult<List<DailyPrayerApiModel>>
    suspend fun getPrayerByTitle(title: String): NetworkResult<DailyPrayerApiModel>
}