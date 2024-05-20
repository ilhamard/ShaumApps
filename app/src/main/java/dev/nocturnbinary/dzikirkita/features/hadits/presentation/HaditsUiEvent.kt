package dev.nocturnbinary.dzikirkita.features.hadits.presentation

sealed class HaditsUiEvent {
    data object RefreshRandomHadits : HaditsUiEvent()
}