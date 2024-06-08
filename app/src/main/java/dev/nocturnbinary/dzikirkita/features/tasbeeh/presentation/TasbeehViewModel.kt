package dev.nocturnbinary.dzikirkita.features.tasbeeh.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TasbeehViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TasbeehUiState())
    val uiState: StateFlow<TasbeehUiState> = _uiState

    fun onEvent(event: TasbeehUiEvent) {
        when (event) {
            is TasbeehUiEvent.IncrementTasbeehCount -> {
                _uiState.value = _uiState.value.copy(tasbeehCount = _uiState.value.tasbeehCount + 1)
                val progress = (_uiState.value.tasbeehCount.toFloat()) / _uiState.value.targetCount
                _uiState.value = _uiState.value.copy(progressCount = progress)
            }

            is TasbeehUiEvent.ResetTasbeehCount -> {
                _uiState.value = _uiState.value.copy(tasbeehCount = 0, progressCount = 0f)
            }

            is TasbeehUiEvent.SetTargetCount -> {
                _uiState.value = _uiState.value.copy(newTargetCount = event.targetCount.toString())
            }

            is TasbeehUiEvent.ToggleVibrateAlert -> {
                _uiState.value =
                    _uiState.value.copy(isVibrateAlert = !_uiState.value.isVibrateAlert)
            }

            is TasbeehUiEvent.ToggleSoundAlert -> {
                _uiState.value = _uiState.value.copy(isSoundAlert = !_uiState.value.isSoundAlert)
            }

            is TasbeehUiEvent.EditTargetCount -> {
                _uiState.value = _uiState.value.copy(isEditMode = !_uiState.value.isEditMode, newTargetCount = _uiState.value.targetCount.toString())
            }

            is TasbeehUiEvent.NewTargetCountChanged -> {
                    _uiState.value = _uiState.value.copy(newTargetCount = event.targetCount)
            }

            is TasbeehUiEvent.ConfirmEditTargetCount -> {
                val newTarget = event.targetCount.toInt()
                if (newTarget >= 0 && newTarget != _uiState.value.targetCount) {
                    _uiState.value = _uiState.value.copy(targetCount = newTarget)
                }
                _uiState.value = _uiState.value.copy(isEditMode = false)
            }
        }
    }
}