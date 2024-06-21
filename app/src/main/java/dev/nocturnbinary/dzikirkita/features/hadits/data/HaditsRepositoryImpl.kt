package dev.nocturnbinary.dzikirkita.features.hadits.data

import dev.nocturnbinary.dzikirkita.network.NetworkResult
import dev.nocturnbinary.dzikirkita.network.RequestHandler
import dev.nocturnbinary.dzikirkita.network.Response
import javax.inject.Inject

class HaditsRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler,
) : HaditsRepository {
    override suspend fun getRandomHadits(
        haditsRiwayat: String,
        noHadits: Int,
    ): NetworkResult<Response<RandomHaditsApiModel>> {
        return requestHandler.get(
            urlPathSegments = listOf("books", haditsRiwayat, "$noHadits")
        )
    }
}