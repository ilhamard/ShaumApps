package com.dev.dzikirkita.data.remote.response

import com.google.gson.annotations.SerializedName

data class DoaHarianResponseItem(
    @field:SerializedName("ayat")
    val ayat: String,

    @field:SerializedName("doa")
    val doa: String,

    @field:SerializedName("artinya")
    val artinya: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("latin")
    val latin: String,
)