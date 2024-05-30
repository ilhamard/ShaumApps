package dev.nocturnbinary.dzikirkita.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object PrayerSchedule : Screen("prayer_schedule")
    data object Qibla : Screen("qibla")
    data object DailyPrayer : Screen("daily_prayer")
    data object DailyPrayerDetail : Screen("daily_prayer_detail")
    data object Tasbeeh : Screen("tasbeeh")
    data object AsmaulHusna : Screen("asmaul_husna")
    data object Hadits : Screen("hadits")
    data object Article : Screen("article")
    data object Quotes : Screen("quotes")
}