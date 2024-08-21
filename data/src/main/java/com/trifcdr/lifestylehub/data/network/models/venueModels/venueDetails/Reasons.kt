package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reasons(
    @SerialName("count")
    val count: Int,
    @SerialName("items")
    val items: List<ItemX>
)