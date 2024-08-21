package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HereNow(
    @SerialName("count")
    val count: Int? = null,
    @SerialName("summary")
    val summary: String? = null
)