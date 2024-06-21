package dev.nocturnbinary.dzikirkita.features.prayerschedule.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PrayerScheduleApiModel(
    val code: Int,
    val data: Data,
    val status: String,
)

@Serializable
data class Offset(
    @SerialName("Sunset")
    val sunset: Int,

    @SerialName("Asr")
    val asr: Int,

    @SerialName("Isha")
    val isha: Int,

    @SerialName("Fajr")
    val fajr: Int,

    @SerialName("Dhuhr")
    val dhuhr: Int,

    @SerialName("Maghrib")
    val maghrib: Int,

    @SerialName("Sunrise")
    val sunrise: Int,

    @SerialName("Midnight")
    val midnight: Int,

    @SerialName("Imsak")
    val imsak: Int,
)

@Serializable
data class Gregorian(
    val date: String,
    val month: Month,
    val year: String,
    val format: String,
    val weekday: Weekday,
    val designation: Designation,
    val day: String,
)

@Serializable
data class Location(
    val latitude: Double,
    val longitude: Double,
)

@Serializable
data class Weekday(
    val en: String,
    val ar: String? = null,
)

@Serializable
data class Meta(
    val method: Method,
    val offset: Offset,
    val school: String,
    val timezone: String,
    val midnightMode: String,
    val latitude: Double,
    val longitude: Double,
    val latitudeAdjustmentMethod: String,
)

@Serializable
data class Month(
    val number: Int,
    val en: String,
    val ar: String? = null,
)

@Serializable
data class Method(
    val name: String,
    val location: Location,
    val id: Int,
    val params: Params,
)

@Serializable
data class Hijri(
    val date: String,
    val month: Month,
    val holidays: List<JsonElement>,
    val year: String,
    val format: String,
    val weekday: Weekday,
    val designation: Designation,
    val day: String,
)

@Serializable
data class Params(
    @SerialName("Isha")
    val isha: Int,

    @SerialName("Fajr")
    val fajr: Int,
)

@Serializable
data class Timings(
    @SerialName("Sunset")
    val sunset: String,

    @SerialName("Asr")
    val asr: String,

    @SerialName("Isha")
    val isha: String,

    @SerialName("Fajr")
    val fajr: String,

    @SerialName("Dhuhr")
    val dhuhr: String,

    @SerialName("Maghrib")
    val maghrib: String,

    @SerialName("Lastthird")
    val lastthird: String,

    @SerialName("Firstthird")
    val firstthird: String,

    @SerialName("Sunrise")
    val sunrise: String,

    @SerialName("Midnight")
    val midnight: String,

    @SerialName("Imsak")
    val imsak: String,
)

@Serializable
data class Data(
    val date: Date,
    val meta: Meta,
    val timings: Timings,
)

@Serializable
data class Designation(
    val expanded: String,
    val abbreviated: String,
)

@Serializable
data class Date(
    val readable: String,
    val hijri: Hijri,
    val gregorian: Gregorian,
    val timestamp: String,
)