package dev.nocturnbinary.dzikirkita.features.hadits.presentation

data class HaditsUiState(
    val hadits: String = "",
    val haditsRiwayat: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)