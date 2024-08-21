package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Listed(
    @SerialName("count")
    val count: Int,
    @SerialName("groups")
    val groups: List<Group>
)