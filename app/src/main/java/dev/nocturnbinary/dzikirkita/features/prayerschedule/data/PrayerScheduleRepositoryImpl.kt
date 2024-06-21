package dev.nocturnbinary.dzikirkita.features.prayerschedule.data

import dev.nocturnbinary.dzikirkita.network.NetworkResult
import dev.nocturnbinary.dzikirkita.network.RequestHandler
import javax.inject.Inject

class PrayerScheduleRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler,
) : PrayerScheduleRepository {
    override suspend fun getPrayerSchedule(
        date: String,
        city: String,
        country: String,
        method: Int,
    ): NetworkResult<PrayerScheduleApiModel> {

        return requestHandler.get(
            urlPathSegments = listOf("v1", "timingsByCity", date),
            queryParams = mapOf(
                "city" to city,
                "country" to country,
                "method" to method.toString()
            )
        )
    }
}