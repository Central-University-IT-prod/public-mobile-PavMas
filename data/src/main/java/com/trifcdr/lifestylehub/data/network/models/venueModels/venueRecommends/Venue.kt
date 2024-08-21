package com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Venue(
    @SerialName("categories")
    val categories: List<Category>,
    @SerialName("id")
    val id: String,
    @SerialName("location")
    val location: Location? = null,
    @SerialName("name")
    val name: String? = null
)