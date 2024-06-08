package dev.nocturnbinary.dzikirkita.features.hadits.domain

import dev.nocturnbinary.dzikirkita.features.hadits.data.HaditsRepository
import dev.nocturnbinary.dzikirkita.features.hadits.data.RandomHaditsApiModel
import dev.nocturnbinary.dzikirkita.network.NetworkException
import dev.nocturnbinary.dzikirkita.network.NetworkResult
import javax.inject.Inject
import kotlin.random.Random

class GetRandomHaditsUseCase @Inject constructor(
    private val haditsRepository: HaditsRepository
) {

    suspend fun invoke(): Resource<RandomHaditsApiModel> {
        val hrList = mutableListOf(
            "abu-daud",
            "ahmad",
            "bukhari",
            "darimi",
            "ibnu-majah",
            "malik",
            "muslim",
            "nasai",
            "tirmidzi"
        )
        val hrRandom = hrList.random()
        var angka = 1

        when (hrRandom) {
            "abu-daud" -> angka = Random.nextInt(4418) + 1
            "ahmad" -> angka = Random.nextInt(12) + 1
            "bukhari" -> angka = Random.nextInt(6638) + 1
            "darimi" -> angka = Random.nextInt(2949) + 1
            "ibnu-majah" -> angka = Random.nextInt(4285) + 1
            "malik" -> angka = Random.nextInt(1587) + 1
            "muslim" -> angka = Random.nextInt(4930) + 1
            "nasai" -> angka = Random.nextInt(5364) + 1
            "tirmidzi" -> angka = Random.nextInt(3625) + 1
        }

        return when (val result =
            haditsRepository.getRandomHadits(haditsRiwayat = hrRandom, noHadits = angka)) {
            is NetworkResult.Error -> result.toResourceError()
            is NetworkResult.Success -> Resource.Success(result.result.data)
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