package com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String? = null,
    @SerialName("pluralName")
    val pluralName: String? = null,
    @SerialName("primary")
    val primary: Boolean? = null,
    @SerialName("shortName")
    val shortName: String? = null
)