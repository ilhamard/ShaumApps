package dev.nocturnbinary.dzikirkita.features.home.data

import dev.nocturnbinary.dzikirkita.network.NetworkResult
import dev.nocturnbinary.dzikirkita.network.RequestHandler
import dev.nocturnbinary.dzikirkita.network.Response

class HomeRepositoryImpl(
    private val requestHandler: RequestHandler
) : HomeRepository {
    override suspend fun getRandomHadits(haditsRiwayat: String, noHadits: Int): NetworkResult<Response<RandomHaditsApiModel>> {
        return requestHandler.get(
            urlPathSegments = listOf("books", haditsRiwayat, "$noHadits")
        )
    }
}