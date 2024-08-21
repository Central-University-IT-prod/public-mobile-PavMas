package com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Venue(
    @SerialName("bestPhoto")
    val bestPhoto: BestPhoto? = null,
    @SerialName("categories")
    val categories: List<Category>,
    @SerialName("contact")
    val contact: Contact? = null,
    @SerialName("hereNow")
    val hereNow: HereNow? = null,
    @SerialName("hours")
    val hours: Hours? = null,
    @SerialName("id")
    val id: String,
    @SerialName("likes")
    val likes: Likes? = null,
    @SerialName("listed")
    val listed: Listed? = null,
    @SerialName("location")
    val location: Location? = null,
    @SerialName("name")
    val name: String,
    @SerialName("reasons")
    val reasons: Reasons? = null,
    @SerialName("url")
    val url: String? = null
)