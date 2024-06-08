package dev.nocturnbinary.dzikirkita.features.dailyprayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nocturnbinary.dzikirkita.features.dailyprayer.domain.GetDailyPrayerUseCase
import dev.nocturnbinary.dzikirkita.features.dailyprayer.domain.GetPrayerDetailUseCase
import dev.nocturnbinary.dzikirkita.features.dailyprayer.domain.GetSearchDailyPrayerUseCase
import dev.nocturnbinary.dzikirkita.features.dailyprayer.domain.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyPrayerViewModel @Inject constructor(
    private val getDailyPrayerUseCase: GetDailyPrayerUseCase,
    private val getPrayerDetailUseCase: GetPrayerDetailUseCase,
    private val getSearchDailyPrayerUseCase: GetSearchDailyPrayerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyPrayerUiState())
    val uiState: StateFlow<DailyPrayerUiState> = _uiState

    private val _prayerDetailState = MutableStateFlow(DailyPrayerUiState())
    val prayerDetailState: StateFlow<DailyPrayerUiState> = _prayerDetailState

    init {
        getDailyPrayer()
    }

    fun onEvent(event: DailyPrayerUiEvent) {
        when (event) {
            is DailyPrayerUiEvent.SearchQueryChanged -> {
                if (event.query.isBlank()) {
                    getDailyPrayer()
                    return
                }
                getSearchDailyPrayer(event.query)
                _uiState.value = _uiState.value.copy(searchQuery = event.query)
            }
        }
    }

    private fun getDailyPrayer() = viewModelScope.launch {
        _uiState.value = DailyPrayerUiState(isLoading = true)
        when (val result = getDailyPrayerUseCase.invoke()) {
            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(
                    dailyPrayer = result.result,
                    isLoading = false
                )
            }

            is Resource.Error -> {
                _uiState.value =
                    _uiState.value.copy(isLoading = false, errorMessage = result.e.name)
            }
        }
    }

    fun getPrayerDetail(id: String) = viewModelScope.launch {
        _prayerDetailState.value = DailyPrayerUiState(isLoading = true)
        when (val result = getPrayerDetailUseCase.invoke(id)) {
            is Resource.Success -> {
                _prayerDetailState.value = _prayerDetailState.value.copy(
                    dailyPrayer = result.result,
                    isLoading = false
                )
            }

            is Resource.Error -> {
                _prayerDetailState.value =
                    _prayerDetailState.value.copy(isLoading = false, errorMessage = result.e.name)
            }
        }
    }

    private fun getSearchDailyPrayer(title: String) = viewModelScope.launch {
        _uiState.value = DailyPrayerUiState(isLoading = true)
        when (val result = getSearchDailyPrayerUseCase.invoke(title)) {
            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(
                    dailyPrayer = arrayListOf(result.result),
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