package com.dev.shaumapps.data.remote.response

import com.google.gson.annotations.SerializedName

data class HadisResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: DataHadis,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("error")
    val error: Boolean,
)

data class DataHadis(

    @field:SerializedName("contents")
    val contents: Contents,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("available")
    val available: Int,

    @field:SerializedName("id")
    val id: String,
)

data class Contents(

    @field:SerializedName("number")
    val number: Int,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("arab")
    val arab: String,
)
