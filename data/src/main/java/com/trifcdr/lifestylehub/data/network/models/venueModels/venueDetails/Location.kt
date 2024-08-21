package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


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
    @SerialName("crossStreet")
    val crossStreet: String? = null,
    @SerialName("formattedAddress")
    val formattedAddress: List<String>,
    @SerialName("lat")
    val lat: Double? = null,
    @SerialName("lng")
    val lng: Double? = null,
    @SerialName("postalCode")
    val postalCode: String? = null,
    @SerialName("state")
    val state: String? = null
)