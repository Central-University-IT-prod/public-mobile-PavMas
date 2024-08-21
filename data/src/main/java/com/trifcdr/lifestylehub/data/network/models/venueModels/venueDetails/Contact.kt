package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    @SerialName("formattedPhone")
    val formattedPhone: String? = null,
    @SerialName("phone")
    val phone: String? = null
)