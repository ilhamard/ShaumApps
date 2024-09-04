package dev.nocturnbinary.dzikirkita.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.nocturnbinary.dzikirkita.R
import dev.nocturnbinary.dzikirkita.ui.theme.background
import dev.nocturnbinary.dzikirkita.ui.theme.textTwo
import dev.nocturnbinary.dzikirkita.utils.DzikirKitaPreview

@Composable
fun PrayerScheduleItem(
    wakltuShalat: String,
    namaShalat: String,
    image: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = image),
            contentDescription = "Imsak",
            modifier = Modifier
                .size(20.dp)
                .weight(1f),
            tint = textTwo
        )
        Text(
            text = namaShalat,
            textAlign = TextAlign.Center,
            color = textTwo,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(1.5f))
        Text(
            text = wakltuShalat,
            textAlign = TextAlign.Center,
            color = textTwo,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = { },
            colors = IconButtonDefaults.iconButtonColors(contentColor = textTwo),
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null)
        }
    }
}

@Composable
@DzikirKitaPreview
fun PreviewPrayerScheduleItem() {
    PrayerScheduleItem(
        wakltuShalat = "Subuh",
        namaShalat = "04:00",
        image = R.drawable.imsak
    )
}