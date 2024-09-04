package dev.nocturnbinary.dzikirkita.features.prayerschedule.presentation

sealed class PrayerScheduleUiEvent {
    data object PreviousDate: PrayerScheduleUiEvent()
    data object NextDate: PrayerScheduleUiEvent()
}