package com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    @SerialName("results")
    val results: List<Result>? = null,
    @SerialName("totalResults")
    val totalResults: Int
)