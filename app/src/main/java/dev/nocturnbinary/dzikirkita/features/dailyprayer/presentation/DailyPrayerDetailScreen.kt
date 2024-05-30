package dev.nocturnbinary.dzikirkita.features.dailyprayer.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerApiModel
import dev.nocturnbinary.dzikirkita.ui.theme.text
import dev.nocturnbinary.dzikirkita.utils.DzikirKitaPreview

@Composable
fun DailyPrayerDetailScreen(
    viewModel: DailyPrayerViewModel,
    id: String
) {
    LaunchedEffect(Unit) {
        viewModel.getPrayerDetail(id)
    }
    val uiState by viewModel.prayerDetailState.collectAsStateWithLifecycle()

    DailyPrayerDetail(uiState = uiState)
}

@Composable
fun DailyPrayerDetail(
    modifier: Modifier = Modifier,
    uiState: DailyPrayerUiState,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ElevatedCard(
            shape = RectangleShape,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.2f)) {
                    Icon(imageVector = Icons.Default.NavigateBefore, contentDescription = "Back")
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    uiState.dailyPrayer.lastOrNull()?.let { Text(text = it.id, color = text) }
                    uiState.dailyPrayer.lastOrNull()?.let {
                        Text(
                            text = it.doa,
                            color = text,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.2f)) {
                    Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "Next")
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                uiState.dailyPrayer.lastOrNull()?.let {
                    Text(
                        text = it.ayat,
                        color = text,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                }
                uiState.dailyPrayer.lastOrNull()?.let {
                    Text(
                        text = "Latin : ${it.latin}",
                        color = text,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
                uiState.dailyPrayer.lastOrNull()?.let {
                    Text(
                        text = "Artinya : ${it.artinya}",
                        color = text,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
@DzikirKitaPreview
fun DailyPrayerDetailPreview() {
    DailyPrayerDetail(
        uiState = DailyPrayerUiState(
            dailyPrayer = listOf(
                DailyPrayerApiModel(
                    "1",
                    "Doa",
                    "Ayat",
                    "Latin",
                    "Artinya"
                )
            )
        )
    )
}
