package com.trifcdr.lifestylehub.data.network.models.venueModels.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("userId")
    val userId: String
)