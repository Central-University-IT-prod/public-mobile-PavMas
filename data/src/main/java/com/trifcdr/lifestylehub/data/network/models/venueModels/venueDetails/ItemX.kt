package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemX(
    @SerialName("reasonName")
    val reasonName: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("type")
    val type: String
)