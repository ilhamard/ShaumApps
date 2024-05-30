package dev.nocturnbinary.dzikirkita.features.dailyprayer.data

import kotlinx.serialization.Serializable

@Serializable
data class DailyPrayerApiModel(
    val ayat: String,
    val doa: String,
    val artinya: String,
    val id: String,
    val latin: String,
)