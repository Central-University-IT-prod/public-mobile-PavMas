package com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupX(
    @SerialName("count")
    val count: Int? = null,
    @SerialName("items")
    val items: List<Item>,

)