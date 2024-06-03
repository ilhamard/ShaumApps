package dev.nocturnbinary.dzikirkita.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nocturnbinary.dzikirkita.R
import dev.nocturnbinary.dzikirkita.ui.theme.secondary
import dev.nocturnbinary.dzikirkita.ui.theme.textTwo
import dev.nocturnbinary.dzikirkita.utils.DzikirKitaPreview

@Composable
fun HeroItemHome(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int = R.drawable.ihwan,
    title: String = "Jadwal Shalat",
    moveToDetail: () -> Unit = {}
    ) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(secondary)
                .clickable { moveToDetail() },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Ikhwan",
                modifier = Modifier.size(42.dp),
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(title, color = textTwo, textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
private fun HeroItemHomePreview() {
    HeroItemHome()
}