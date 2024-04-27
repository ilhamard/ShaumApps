package dev.nocturnbinary.dzikirkita.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nocturnbinary.dzikirkita.features.home.domain.GetRandomHaditsUseCase
import dev.nocturnbinary.dzikirkita.features.home.domain.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val randomHaditsUseCase: GetRandomHaditsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        getRandomHadits()
    }

    private fun getRandomHadits() = viewModelScope.launch {
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