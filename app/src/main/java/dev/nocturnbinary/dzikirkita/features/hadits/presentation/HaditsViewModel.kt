package dev.nocturnbinary.dzikirkita.features.hadits.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nocturnbinary.dzikirkita.features.hadits.domain.GetRandomHaditsUseCase
import dev.nocturnbinary.dzikirkita.features.hadits.domain.Resource
import dev.nocturnbinary.dzikirkita.features.home.presentation.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HaditsViewModel @Inject constructor(
    private val randomHaditsUseCase: GetRandomHaditsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HaditsUiState())
    val uiState: StateFlow<HaditsUiState> = _uiState

    init {
        getRandomHadits()
    }

    fun onEvent(event: HaditsUiEvent) {
        when (event) {
            HaditsUiEvent.RefreshRandomHadits -> getRandomHadits()
        }
    }

    private fun getRandomHadits() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        when (val result = randomHaditsUseCase.invoke()) {
            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(
                    hadits = result.result.contents.id,
                    haditsRiwayat = result.result.name,
                    isLoading = false
                )
            }

            is Resource.Error -> {
                _uiState.value =
                    _uiState.value.copy(isLoading = false, errorMessage = result.e.name)
            }
        }
    }
}