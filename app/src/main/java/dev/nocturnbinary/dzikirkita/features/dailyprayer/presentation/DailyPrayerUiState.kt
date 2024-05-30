package dev.nocturnbinary.dzikirkita.features.dailyprayer.presentation

import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerApiModel

data class DailyPrayerUiState(
    val dailyPrayer: List<DailyPrayerApiModel> = listOf(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)