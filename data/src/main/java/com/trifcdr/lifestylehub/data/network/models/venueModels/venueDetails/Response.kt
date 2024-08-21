package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("venue")
    val venue: Venue
)