package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    @SerialName("count")
    val count: Int,
    @SerialName("items")
    val itemDS: List<ItemD>,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: String
)