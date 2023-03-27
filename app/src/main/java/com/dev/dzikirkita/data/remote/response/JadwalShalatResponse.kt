package com.dev.dzikirkita.data.remote.response

import com.google.gson.annotations.SerializedName

data class JadwalShalatResponse(
    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("status")
    val status: String,
)

data class Offset(
    @field:SerializedName("Sunset")
    val sunset: Int,

    @field:SerializedName("Asr")
    val asr: Int,

    @field:SerializedName("Isha")
    val isha: Int,

    @field:SerializedName("Fajr")
    val fajr: Int,

    @field:SerializedName("Dhuhr")
    val dhuhr: Int,

    @field:SerializedName("Maghrib")
    val maghrib: Int,

    @field:SerializedName("Sunrise")
    val sunrise: Int,

    @field:SerializedName("Midnight")
    val midnight: Int,

    @field:SerializedName("Imsak")
    val imsak: Int,
)

data class Gregorian(
    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("month")
    val month: Month,

    @field:SerializedName("year")
    val year: String,

    @field:SerializedName("format")
    val format: String,

    @field:SerializedName("weekday")
    val weekday: Weekday,

    @field:SerializedName("designation")
    val designation: Designation,

    @field:SerializedName("day")
    val day: String,
)

data class Location(
    @field:SerializedName("latitude")
    val latitude: Any,

    @field:SerializedName("longitude")
    val longitude: Any,
)

data class Weekday(
    @field:SerializedName("en")
    val en: String,

    @field:SerializedName("ar")
    val ar: String,
)

data class Meta(
    @field:SerializedName("method")
    val method: Method,

    @field:SerializedName("offset")
    val offset: Offset,

    @field:SerializedName("school")
    val school: String,

    @field:SerializedName("timezone")
    val timezone: String,

    @field:SerializedName("midnightMode")
    val midnightMode: String,

    @field:SerializedName("latitude")
    val latitude: Any,

    @field:SerializedName("longitude")
    val longitude: Any,

    @field:SerializedName("latitudeAdjustmentMethod")
    val latitudeAdjustmentMethod: String,
)

data class Month(
    @field:SerializedName("number")
    val number: Int,

    @field:SerializedName("en")
    val en: String,

    @field:SerializedName("ar")
    val ar: String,
)

data class Method(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("location")
    val location: Location,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("params")
    val params: Params,
)

data class Hijri(
    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("month")
    val month: Month,

    @field:SerializedName("holidays")
    val holidays: List<Any>,

    @field:SerializedName("year")
    val year: String,

    @field:SerializedName("format")
    val format: String,

    @field:SerializedName("weekday")
    val weekday: Weekday,

    @field:SerializedName("designation")
    val designation: Designation,

    @field:SerializedName("day")
    val day: String,
)

data class Params(
    @field:SerializedName("Isha")
    val isha: Int,

    @field:SerializedName("Fajr")
    val fajr: Int,
)

data class Timings(
    @field:SerializedName("Sunset")
    val sunset: String,

    @field:SerializedName("Asr")
    val asr: String,

    @field:SerializedName("Isha")
    val isha: String,

    @field:SerializedName("Fajr")
    val fajr: String,

    @field:SerializedName("Dhuhr")
    val dhuhr: String,

    @field:SerializedName("Maghrib")
    val maghrib: String,

    @field:SerializedName("Lastthird")
    val lastthird: String,

    @field:SerializedName("Firstthird")
    val firstthird: String,

    @field:SerializedName("Sunrise")
    val sunrise: String,

    @field:SerializedName("Midnight")
    val midnight: String,

    @field:SerializedName("Imsak")
    val imsak: String,
)

data class Data(
    @field:SerializedName("date")
    val date: Date,

    @field:SerializedName("meta")
    val meta: Meta,

    @field:SerializedName("timings")
    val timings: Timings,
)

data class Designation(
    @field:SerializedName("expanded")
    val expanded: String,

    @field:SerializedName("abbreviated")
    val abbreviated: String,
)

data class Date(
    @field:SerializedName("readable")
    val readable: String,

    @field:SerializedName("hijri")
    val hijri: Hijri,

    @field:SerializedName("gregorian")
    val gregorian: Gregorian,

    @field:SerializedName("timestamp")
    val timestamp: String,
)
