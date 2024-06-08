package dev.nocturnbinary.dzikirkita.features.dailyprayer.domain

import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerApiModel
import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerRepository
import dev.nocturnbinary.dzikirkita.network.NetworkException
import dev.nocturnbinary.dzikirkita.network.NetworkResult
import javax.inject.Inject

class GetDailyPrayerUseCase @Inject constructor(
    private val dailyPrayerRepository: DailyPrayerRepository
) {
    suspend fun invoke(): Resource<List<DailyPrayerApiModel>> {
        return when (val result = dailyPrayerRepository.getDailyPrayer()) {
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