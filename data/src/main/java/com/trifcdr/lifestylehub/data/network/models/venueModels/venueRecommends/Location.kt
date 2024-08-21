package com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("address")
    val address: String? = null,
    @SerialName("cc")
    val cc: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("distance")
    val distance: Int? = null,
    @SerialName("formattedAddress")
    val formattedAddress: List<String>,
    @SerialName("state")
    val state: String? = null
)