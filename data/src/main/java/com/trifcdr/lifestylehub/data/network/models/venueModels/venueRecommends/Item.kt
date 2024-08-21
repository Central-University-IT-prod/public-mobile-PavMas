package com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("id")
    val id: String? = null,
    @SerialName("prefix")
    val prefix: String? = null,
    @SerialName("suffix")
    val suffix: String? = null,
    @SerialName("visibility")
    val visibility: String? = null,

)