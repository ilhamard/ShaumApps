package dev.nocturnbinary.dzikirkita.features.tasbeeh.presentation

sealed class TasbeehUiEvent {
    data object IncrementTasbeehCount : TasbeehUiEvent()
    data object ResetTasbeehCount : TasbeehUiEvent()
    data class SetTargetCount(val targetCount: Int) : TasbeehUiEvent()
    data object ToggleVibrateAlert : TasbeehUiEvent()
    data object ToggleSoundAlert : TasbeehUiEvent()

}