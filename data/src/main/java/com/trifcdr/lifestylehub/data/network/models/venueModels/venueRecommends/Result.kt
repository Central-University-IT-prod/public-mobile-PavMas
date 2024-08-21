package com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("id")
    val id: String,
    @SerialName("photo")
    val photo: Photo? = null,
    @SerialName("venue")
    val venue: Venue
)