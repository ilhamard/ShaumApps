package dev.nocturnbinary.dzikirkita

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.nocturnbinary.dzikirkita.features.asmaulhusna.presentation.AsmaulHusnaScreen
import dev.nocturnbinary.dzikirkita.features.dailyprayer.presentation.DailyPrayerDetailScreen
import dev.nocturnbinary.dzikirkita.features.dailyprayer.presentation.DailyPrayerScreen
import dev.nocturnbinary.dzikirkita.features.hadits.presentation.HaditsScreen
import dev.nocturnbinary.dzikirkita.features.home.presentation.HomeScreen
import dev.nocturnbinary.dzikirkita.features.prayerschedule.presentation.PrayerScheduleScreen
import dev.nocturnbinary.dzikirkita.features.qibla.QiblaCompassScreen
import dev.nocturnbinary.dzikirkita.features.tasbeeh.TasbeehScreen
import dev.nocturnbinary.dzikirkita.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DzikirKitaApp(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute !in listOf(
                    Screen.Home.route,
                    Screen.Tasbeeh.route,
                )
            ) {
                TopAppBar(
                    title = { Text(text = "Dzikir Kita") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Kembali"
                            )
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(viewModel = hiltViewModel(), navController = navController)
            }
            composable(Screen.Hadits.route) {
                HaditsScreen(viewModel = hiltViewModel())
            }
            composable(Screen.DailyPrayer.route) {
                DailyPrayerScreen(viewModel = hiltViewModel(), navController = navController)
            }
            composable("${Screen.DailyPrayerDetail.route}/{id}") {
                val id = it.arguments?.getString("id")
                DailyPrayerDetailScreen(viewModel = hiltViewModel(), id = id.toString())
            }
            composable(Screen.Tasbeeh.route) {
                TasbeehScreen(viewModel = viewModel(), navController = navController)
            }
            composable(Screen.Qibla.route) {
                QiblaCompassScreen(viewModel = hiltViewModel())
            }
            composable(Screen.AsmaulHusna.route) {
                AsmaulHusnaScreen(viewModel = hiltViewModel())
            }
            composable(Screen.PrayerSchedule.route){
                PrayerScheduleScreen(viewModel = hiltViewModel())
            }
        }
    }
}