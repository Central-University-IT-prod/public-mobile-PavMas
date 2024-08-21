package com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    @SerialName("createdAt")
    val createdAt: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("id")
    val id: String,
    @SerialName("prefix")
    val prefix: String,
    @SerialName("suffix")
    val suffix: String,
    @SerialName("visibility")
    val visibility: String,
    @SerialName("width")
    val width: Int
)