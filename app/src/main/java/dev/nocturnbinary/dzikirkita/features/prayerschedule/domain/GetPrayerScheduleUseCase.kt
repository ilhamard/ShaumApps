package dev.nocturnbinary.dzikirkita.features.prayerschedule.domain

import dev.nocturnbinary.dzikirkita.features.prayerschedule.data.PrayerScheduleApiModel
import dev.nocturnbinary.dzikirkita.features.prayerschedule.data.PrayerScheduleRepository
import dev.nocturnbinary.dzikirkita.network.NetworkException
import dev.nocturnbinary.dzikirkita.network.NetworkResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetPrayerScheduleUseCase @Inject constructor(
    private val prayerScheduleRepository: PrayerScheduleRepository,
) {
    suspend fun invoke(
        city: String = "Kota Jakarta Selatan",
        country: String = "Indonesia",
        method: Int = 11,
    ): Resource<PrayerScheduleApiModel> {
        val currentDate = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = formatter.format(currentDate)

        return when (val result = prayerScheduleRepository.getPrayerSchedule(
            date = formattedDate,
            city = city,
            country = country,
            method = method,
        )) {
            is NetworkResult.Error -> result.toResourceError()
            is NetworkResult.Success -> Resource.Success(result.result)
        }
    }
}

fun NetworkResult.Error<*>.toResourceError(): Resource.Error {
    return when (exception) {
        is NetworkException.NotFoundException -> Resource.Error(
            ResourceError.SERVICE_UNAVAILABLE,
            exception.message
        )

        is NetworkException.UnauthorizedException -> Resource.Error(
            ResourceError.UNAUTHORIZED,
            exception.message
        )

        is NetworkException.UnknownException -> Resource.Error(
            ResourceError.UNKNOWN,
            exception.message
        )

        is NetworkException.ForbiddenException -> Resource.Error(
            ResourceError.FORBIDDEN,
            exception.message
        )

        is NetworkException.BadRequestException -> Resource.Error(
            ResourceError.BAD_REQUEST,
            exception.message
        )
    }
}