package dev.nocturnbinary.dzikirkita.features.home.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.nocturnbinary.dzikirkita.R
import dev.nocturnbinary.dzikirkita.ui.components.HeroItemHome
import dev.nocturnbinary.dzikirkita.ui.navigation.Screen
import dev.nocturnbinary.dzikirkita.ui.theme.background
import dev.nocturnbinary.dzikirkita.ui.theme.primary
import dev.nocturnbinary.dzikirkita.ui.theme.secondary
import dev.nocturnbinary.dzikirkita.ui.theme.text
import dev.nocturnbinary.dzikirkita.ui.theme.textTwo

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Home(
        uiState = uiState.value,
        moveTo = { navController.navigate(it.route) }
    )
}

@Composable
fun Home(
    uiState: HomeUiState,
    moveTo: (Screen) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 32.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(secondary),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ihwan),
                        contentDescription = "Ikhwan"
                    )
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(text = "Assalamu'alaikum", color = textTwo)
                    Text(
                        text = "Selamat datang di Dzikir Kita",
                        style = MaterialTheme.typography.titleSmall,
                        color = text
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifikasi",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = primary,
                    contentColor = Color.White
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 2f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_masjid),
                        contentDescription = "Masjid",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        alpha = 0.2f
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
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
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ashar 15:23 WIB",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "- 2 : 40 : 43", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                HeroItemHome(
                    image = R.drawable.jam,
                    title = "Jadwal Shalat",
                    modifier = Modifier.weight(1f)
                )
                HeroItemHome(
                    image = R.drawable.kiblat,
                    title = "Kiblat",
                    modifier = Modifier.weight(1f),
                    moveToDetail = { moveTo(Screen.Qibla) }
                )
                HeroItemHome(
                    image = R.drawable.doa,
                    title = "Doa Harian",
                    modifier = Modifier.weight(1f),
                    moveToDetail = { moveTo(Screen.DailyPrayer) }
                )
                HeroItemHome(
                    image = R.drawable.tasbih,
                    title = "Tasbih",
                    modifier = Modifier.weight(1f),
                    moveToDetail = { moveTo(Screen.Tasbeeh) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                HeroItemHome(
                    image = R.drawable.asmaulhusna,
                    title = "Asmaul Husna",
                    modifier = Modifier.weight(1f),
                    moveToDetail = { moveTo(Screen.AsmaulHusna) }
                )
                HeroItemHome(
                    image = R.drawable.hadits,
                    title = "Hadits",
                    modifier = Modifier.weight(1f),
                    moveToDetail = { moveTo(Screen.Hadits) }
                )
                HeroItemHome(
                    image = R.drawable.artikel,
                    title = "Artikel",
                    modifier = Modifier.weight(1f)
                )
                HeroItemHome(
                    image = R.drawable.kalam,
                    title = "Kutipan",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "Hadits",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = text,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(width = 1.dp, color = primary),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.outlinedCardColors(
                    containerColor = background
                ),
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
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
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                        Text(
                            text = uiState.haditsRiwayat,
                            style = MaterialTheme.typography.titleSmall,
                            color = primary,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    Home(uiState = HomeUiState(), moveTo = {})
}