package dev.nocturnbinary.dzikirkita.features.dailyprayer.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.nocturnbinary.dzikirkita.ui.components.PrayerOrAsmaulHusnaItem
import dev.nocturnbinary.dzikirkita.ui.navigation.Screen
import dev.nocturnbinary.dzikirkita.utils.DzikirKitaPreview

@Composable
fun DailyPrayerScreen(
    viewModel: DailyPrayerViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DailyPrayer(uiState = uiState, onEvent = { viewModel.onEvent(it) }, moveToDetail = {
        navController.navigate("${Screen.DailyPrayerDetail.route}/$it")
    })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DailyPrayer(
    uiState: DailyPrayerUiState,
    onEvent: (DailyPrayerUiEvent) -> Unit,
    moveToDetail: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = { onEvent(DailyPrayerUiEvent.SearchQueryChanged(it)) },
            onSearch = { keyboardController?.hide() },
            active = false,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            placeholder = {
                Text("Cari doa harian")
            },
            shape = MaterialTheme.shapes.medium,
            colors = SearchBarDefaults.colors(
                containerColor = Color.LightGray,
            ),
            trailingIcon = {
                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onEvent(DailyPrayerUiEvent.SearchQueryChanged("")) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = Color.Gray
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        ) {
            ListPrayer(uiState = uiState, moveToDetail = moveToDetail)
        }
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ListPrayer(uiState, moveToDetail)
        }
    }
}

@Composable
fun ListPrayer(uiState: DailyPrayerUiState, moveToDetail: (String) -> Unit) {
    LazyColumn {
        items(uiState.dailyPrayer) { prayer ->
            PrayerOrAsmaulHusnaItem(
                modifier = Modifier.padding(vertical = 4.dp),
                no = prayer.id.toInt(),
                title = prayer.doa,
                moveToDetail = { moveToDetail(prayer.id) }
            )
        }
    }
}

@Composable
@DzikirKitaPreview
fun DailyPrayerPreview() {
    DailyPrayer(uiState = DailyPrayerUiState(), onEvent = {}, moveToDetail = {})
}