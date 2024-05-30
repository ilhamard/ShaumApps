package dev.nocturnbinary.dzikirkita.features.dailyprayer.presentation

sealed class DailyPrayerUiEvent {
    data class SearchQueryChanged(val query: String) : DailyPrayerUiEvent()
}

sealed class DailyPrayerDetailUiEvent {

}