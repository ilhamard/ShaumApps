package dev.nocturnbinary.dzikirkita.features.home.presentation

data class HomeUiState(
    val hadits: String = "",
    val haditsRiwayat: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
