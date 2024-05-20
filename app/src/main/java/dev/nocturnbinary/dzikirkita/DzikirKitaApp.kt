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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.nocturnbinary.dzikirkita.features.hadits.presentation.HaditsScreen
import dev.nocturnbinary.dzikirkita.features.home.presentation.HomeScreen
import dev.nocturnbinary.dzikirkita.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DzikirKitaApp(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute !in listOf(
                    Screen.Home.route
                )
            ) {
                TopAppBar(
                    title = { Text(text = "Dzikir Kita") },
                    navigationIcon = {
                        IconButton(onClick = {  }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Kembali"
                            )
                        }
                    }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(viewModel = hiltViewModel(), navController = navController)
            }
            composable(Screen.Hadits.route){
                HaditsScreen(viewModel = hiltViewModel())
            }
        }
    }
}