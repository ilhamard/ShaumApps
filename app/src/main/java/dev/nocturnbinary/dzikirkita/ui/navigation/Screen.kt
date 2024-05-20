package dev.nocturnbinary.dzikirkita.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object JadwalShalat : Screen("jadwal_shalat")
    data object Kiblat : Screen("kiblat")
    data object DoaHarian : Screen("doa_harian")
    data object Tasbih : Screen("tasbih")
    data object AsmaulHusna : Screen("asmaul_husna")
    data object Hadits : Screen("hadits")
    data object Artikel : Screen("artikel")
    data object Kutipan : Screen("kutipan")
}