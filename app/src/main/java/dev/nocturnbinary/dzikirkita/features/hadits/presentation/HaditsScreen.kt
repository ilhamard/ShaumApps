package dev.nocturnbinary.dzikirkita.features.hadits.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nocturnbinary.dzikirkita.ui.theme.DzikirKitaTheme
import dev.nocturnbinary.dzikirkita.ui.theme.primary
import dev.nocturnbinary.dzikirkita.utils.DzikirKitaPreview

@Composable
fun HaditsScreen(
    viewModel: HaditsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Hadits(uiState = uiState, onEvent = { viewModel.onEvent(it) })
}

@Composable
fun Hadits(
    uiState: HaditsUiState,
    onEvent: (HaditsUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onEvent(HaditsUiEvent.RefreshRandomHadits) }) {
                Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = null)
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
            ) {
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Text(
                        text = uiState.hadits,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = uiState.haditsRiwayat,
                        style = MaterialTheme.typography.titleMedium,
                        color = primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
            IconButton(onClick = { onEvent(HaditsUiEvent.RefreshRandomHadits) }) {
                Icon(imageVector = Icons.Default.SkipNext, contentDescription = null)
            }
        }
    }
}

@Composable
@DzikirKitaPreview
private fun HaditsPreview() {
    DzikirKitaTheme {
        Hadits(uiState = HaditsUiState(hadits = "Hadits", haditsRiwayat = "Riwayat"), onEvent = {})
    }
}