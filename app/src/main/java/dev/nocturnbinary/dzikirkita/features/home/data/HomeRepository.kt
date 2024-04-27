package dev.nocturnbinary.dzikirkita.features.home.data

import dev.nocturnbinary.dzikirkita.network.NetworkResult
import dev.nocturnbinary.dzikirkita.network.Response

interface HomeRepository {
    suspend fun getRandomHadits(haditsRiwayat: String, noHadits: Int): NetworkResult<Response<RandomHaditsApiModel>>
}