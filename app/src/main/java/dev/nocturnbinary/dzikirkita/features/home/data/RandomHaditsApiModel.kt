package dev.nocturnbinary.dzikirkita.features.home.data

import kotlinx.serialization.Serializable

@Serializable
data class RandomHaditsApiModel(
    val contents: Contents,
    val name: String,
    val available: Int,
    val id: String,
)

@Serializable
data class Contents(
    val number: Int,
    val id: String,
    val arab: String,
)
