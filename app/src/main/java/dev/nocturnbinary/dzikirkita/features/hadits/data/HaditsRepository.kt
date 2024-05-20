package dev.nocturnbinary.dzikirkita.features.hadits.data

import dev.nocturnbinary.dzikirkita.network.NetworkResult
import dev.nocturnbinary.dzikirkita.network.Response

interface HaditsRepository {
    suspend fun getRandomHadits(haditsRiwayat: String, noHadits: Int): NetworkResult<Response<RandomHaditsApiModel>>
}