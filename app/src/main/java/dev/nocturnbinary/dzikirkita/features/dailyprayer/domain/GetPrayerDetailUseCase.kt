package dev.nocturnbinary.dzikirkita.features.dailyprayer.domain

import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerApiModel
import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerRepository
import dev.nocturnbinary.dzikirkita.network.NetworkResult
import javax.inject.Inject

class GetPrayerDetailUseCase @Inject constructor(
    private val dailyPrayerRepository: DailyPrayerRepository
) {
    suspend fun invoke(title: String): Resource<List<DailyPrayerApiModel>> {
        return when (val result = dailyPrayerRepository.getPrayerById(title)) {
            is NetworkResult.Error -> result.toResourceError()
            is NetworkResult.Success -> Resource.Success(result.result)
        }
    }
}