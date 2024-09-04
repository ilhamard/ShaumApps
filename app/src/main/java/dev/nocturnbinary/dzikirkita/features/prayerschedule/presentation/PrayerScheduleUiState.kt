package dev.nocturnbinary.dzikirkita.features.prayerschedule.presentation

data class PrayerScheduleUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val imsakTime: String = "",
    val subuhTime: String = "",
    val terbitTime: String = "",
    val dzuhurTime: String = "",
    val asharTime: String = "",
    val maghribTime: String = "",
    val isyaTime: String = "",
    val dateHijr: String = "",
    val monthHijr: String = "",
    val yearHijr: String = "",
    val dateMasehi: String = "",
    val nextPrayerTime: String = "Shubuh, 04:22 WIB",
    val remainingTime: String = "00:00:00",
)
