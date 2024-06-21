package dev.nocturnbinary.dzikirkita.features.home.presentation

data class HomeUiState(
    val hadits: String = "",
    val haditsRiwayat: String = "",
    val nextPrayerTime: String = "Shubuh, 04:22 WIB",
    val remainingTime: String = "00:00:00",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)
