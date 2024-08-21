package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Likes(
    @SerialName("count")
    val count: Int,
    @SerialName("summary")
    val summary: String
)