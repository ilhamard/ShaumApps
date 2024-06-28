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
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.nocturnbinary.dzikirkita.R
import dev.nocturnbinary.dzikirkita.ui.theme.background
import dev.nocturnbinary.dzikirkita.ui.theme.primary
import dev.nocturnbinary.dzikirkita.utils.DzikirKitaPreview

@Composable
fun PrayerScheduleScreen(modifier: Modifier = Modifier) {

}

@Composable
fun PrayerSchedule(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
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
                modifier = Modifier.padding(top = 32.dp)
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
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Shubuh, 04:40 WIB",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "- 00:00:00",
                    style = MaterialTheme.typography.titleLarge
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
                    .padding(vertical = 6.dp)
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = "Sebelumnya"
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tanggal Masehi",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Text(text = "Tanggal Hijriah", style = MaterialTheme.typography.bodyMedium)
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.NavigateNext,
                        contentDescription = "Selanjutnya"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        PrayerScheduleItem(wakltuShalat = "04:40", namaShalat = "Imsak", image = R.drawable.imsak)
        PrayerScheduleItem(wakltuShalat = "05:20", namaShalat = "Subuh", image = R.drawable.subuh)
        PrayerScheduleItem(wakltuShalat = "06:40", namaShalat = "Terbit", image = R.drawable.terbit)
        PrayerScheduleItem(wakltuShalat = "08:40", namaShalat = "Dhuha", image = R.drawable.dhuha)
        PrayerScheduleItem(wakltuShalat = "12:44", namaShalat = "Dzuhur", image = R.drawable.dzuhur)
        PrayerScheduleItem(wakltuShalat = "15:10", namaShalat = "Ashar", image = R.drawable.ashar)
        PrayerScheduleItem(
            wakltuShalat = "18:111",
            namaShalat = "Maghrib",
            image = R.drawable.maghrib
        )
        PrayerScheduleItem(wakltuShalat = "17:10", namaShalat = "Isya", image = R.drawable.isya)
        PrayerScheduleItem(
            wakltuShalat = "12:40",
            namaShalat = "Maghrib",
            image = R.drawable.maghrib
        )

    }
}

@Composable
fun PrayerScheduleItem(
    modifier: Modifier = Modifier,
    wakltuShalat: String,
    namaShalat: String,
    image: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = background),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = image),
            contentDescription = "Imsak",
            modifier = Modifier.size(24.dp)
        )
        Text(text = wakltuShalat)
        Text(text = namaShalat)
        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Imsak")
        }
    }
}


@DzikirKitaPreview
@Composable
fun PrayerSchedulePreview() {
    PrayerSchedule()
}