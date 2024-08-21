package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hours(
    @SerialName("isOpen")
    val isOpen: Boolean? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("timeframes")
    val timeframes: List<Timeframe>? = null
)