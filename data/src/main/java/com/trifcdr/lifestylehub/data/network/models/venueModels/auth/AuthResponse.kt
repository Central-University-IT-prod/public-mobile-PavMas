package com.trifcdr.lifestylehub.data.network.models.venueModels.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("response")
    val response: Response
)