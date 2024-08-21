package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BestPhoto(
    @SerialName("height")
    val height: Int,
    @SerialName("id")
    val id: String,
    @SerialName("prefix")
    val prefix: String,
    @SerialName("suffix")
    val suffix: String,
    @SerialName("width")
    val width: Int
)