package dev.nocturnbinary.dzikirkita.features.prayerschedule.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nocturnbinary.dzikirkita.R
import dev.nocturnbinary.dzikirkita.ui.components.PrayerScheduleItem
import dev.nocturnbinary.dzikirkita.ui.theme.primary
import dev.nocturnbinary.dzikirkita.ui.theme.text
import dev.nocturnbinary.dzikirkita.ui.theme.textTwo
import dev.nocturnbinary.dzikirkita.utils.DzikirKitaPreview

@Composable
fun PrayerScheduleScreen(viewModel: PrayerScheduleViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrayerSchedule(uiState = uiState, onEvent = viewModel::onEvent)
}

@Composable
fun PrayerSchedule(
    uiState: PrayerScheduleUiState,
    onEvent: (PrayerScheduleUiEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f / 3f)
                .background(color = primary),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_jadwal_shalat_2),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.bg_jadwal_shalat),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Lokasi",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = "Kota Jakarta Selatan, DKI Jakarta",
                        style = MaterialTheme.typography.bodyMedium,
                        color = textTwo
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = uiState.nextPrayerTime,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = text
                    ),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "- ${uiState.remainingTime}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = textTwo
                    )
                )
            }
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                IconButton(onClick = { onEvent(PrayerScheduleUiEvent.PreviousDate) }) {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = "Sebelumnya",
                        tint = primary
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.dateMasehi,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = text
                        ),
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Text(
                        text = "${uiState.dateHijr} ${uiState.monthHijr} ${uiState.yearHijr}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = textTwo
                        )
                    )
                }
                IconButton(onClick = { onEvent(PrayerScheduleUiEvent.NextDate) }) {
                    Icon(
                        imageVector = Icons.Default.NavigateNext,
                        contentDescription = "Selanjutnya",
                        tint = primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        PrayerScheduleItem(
            wakltuShalat = uiState.imsakTime,
            namaShalat = "Imsak",
            image = R.drawable.imsak
        )
        PrayerScheduleItem(
            wakltuShalat = uiState.subuhTime,
            namaShalat = "Subuh",
            image = R.drawable.subuh
        )
        PrayerScheduleItem(
            wakltuShalat = uiState.terbitTime,
            namaShalat = "Terbit",
            image = R.drawable.terbit
        )
        PrayerScheduleItem(
            wakltuShalat = uiState.dzuhurTime,
            namaShalat = "Dzuhur",
            image = R.drawable.dzuhur
        )
        PrayerScheduleItem(
            wakltuShalat = uiState.asharTime,
            namaShalat = "Ashar",
            image = R.drawable.ashar
        )
        PrayerScheduleItem(
            wakltuShalat = uiState.maghribTime,
            namaShalat = "Maghrib",
            image = R.drawable.maghrib
        )
        PrayerScheduleItem(
            wakltuShalat = uiState.isyaTime,
            namaShalat = "Isya",
            image = R.drawable.isya
        )
    }
}

@DzikirKitaPreview
@Composable
fun PrayerSchedulePreview() {
    PrayerSchedule(uiState = PrayerScheduleUiState(), onEvent = {})
}