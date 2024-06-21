package dev.nocturnbinary.dzikirkita.features.tasbeeh

sealed class TasbeehUiEvent {
    data object IncrementTasbeehCount : TasbeehUiEvent()
    data object ResetTasbeehCount : TasbeehUiEvent()
    data class SetTargetCount(val targetCount: Int) : TasbeehUiEvent()
    data class NewTargetCountChanged(val targetCount: String) : TasbeehUiEvent()
    data object ToggleVibrateAlert : TasbeehUiEvent()
    data object ToggleSoundAlert : TasbeehUiEvent()
    data object EditTargetCount : TasbeehUiEvent()
    data class ConfirmEditTargetCount(val targetCount: String) : TasbeehUiEvent()
}