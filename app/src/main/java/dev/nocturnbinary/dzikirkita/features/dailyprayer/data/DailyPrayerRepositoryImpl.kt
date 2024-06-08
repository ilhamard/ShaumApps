package dev.nocturnbinary.dzikirkita.features.dailyprayer.data

import dev.nocturnbinary.dzikirkita.network.NetworkResult
import dev.nocturnbinary.dzikirkita.network.RequestHandler
import javax.inject.Inject

class DailyPrayerRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler
) : DailyPrayerRepository {
    override suspend fun getDailyPrayer(): NetworkResult<List<DailyPrayerApiModel>> {
        return requestHandler.get(
            urlPathSegments = listOf("api")
        )
    }

    override suspend fun getPrayerById(id: String): NetworkResult<List<DailyPrayerApiModel>> {
        return requestHandler.get(
            urlPathSegments = listOf("api", id)
        )
    }

    override suspend fun getPrayerByTitle(title: String): NetworkResult<DailyPrayerApiModel> {
        return requestHandler.get(
            urlPathSegments = listOf("api", "doa", title)
        )
    }
}