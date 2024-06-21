package dev.nocturnbinary.dzikirkita.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nocturnbinary.dzikirkita.ui.theme.background
import dev.nocturnbinary.dzikirkita.ui.theme.primary
import dev.nocturnbinary.dzikirkita.ui.theme.secondary

@Composable
fun PrayerOrAsmaulHusnaItem(
    modifier: Modifier = Modifier,
    no: Int,
    title: String,
    moveToDetail: () -> Unit = {},
    isUpDownVote: Boolean = false,
    description: String = "",
) {
    var isDescriptionVisible by remember { mutableStateOf(false) }

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
                    .weight(0.20f),
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
            if (isUpDownVote) {
                Column {
                    IconButton(onClick = { isDescriptionVisible = !isDescriptionVisible }) {
                        Icon(
                            imageVector = if (isDescriptionVisible) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                            contentDescription = "Up/Down"
                        )
                    }
                }
            }
        }
        AnimatedVisibility(visible = isDescriptionVisible) {
            Column {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .background(background)
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
@Preview
private fun DailyPrayerItemPreview() {
    PrayerOrAsmaulHusnaItem(no = 1, title = "Judul")
}