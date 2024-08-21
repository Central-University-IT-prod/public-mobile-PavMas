package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("pluralName")
    val pluralName: String,
    @SerialName("shortName")
    val shortName: String
)