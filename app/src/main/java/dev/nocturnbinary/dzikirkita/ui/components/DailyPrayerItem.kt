package dev.nocturnbinary.dzikirkita.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nocturnbinary.dzikirkita.ui.theme.background
import dev.nocturnbinary.dzikirkita.ui.theme.primary
import dev.nocturnbinary.dzikirkita.ui.theme.secondary

@Composable
fun DailyPrayerItem(
    modifier: Modifier = Modifier,
    no: Int,
    title: String,
    moveToDetail: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { moveToDetail() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .background(secondary)
                    .padding(16.dp)
                    .weight(0.14f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = no.toString(),
                    color = primary,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            ) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
@Preview
private fun DailyPrayerItemPreview() {
    DailyPrayerItem(no = 1, title = "Judul", moveToDetail = {})
}