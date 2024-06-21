package dev.nocturnbinary.dzikirkita.features.asmaulhusna.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.nocturnbinary.dzikirkita.features.asmaulhusna.data.AsmaulHusna
import dev.nocturnbinary.dzikirkita.ui.components.PrayerOrAsmaulHusnaItem

@Composable
fun AsmaulHusnaScreen(viewModel: AsmaulHusnaViewModel) {
    val asmaulHusna = viewModel.asmahusna
    AsmaulHusna(asmaulHusna)
}

@Composable
fun AsmaulHusna(listAsmaulHusna: List<AsmaulHusna>) {

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(listAsmaulHusna) { index, asmaulHusna ->
            PrayerOrAsmaulHusnaItem(
                modifier = Modifier.padding(vertical = 4.dp),
                no = index + 1,
                title = asmaulHusna.asmaulHusna,
                isUpDownVote = true,
                description = asmaulHusna.asmaulHusnaMakna
            )
        }
    }
}