package com.trifcdr.lifestylehub.domain.models.venue.venueRecommends


data class Photo(
    val id: String,
    val prefix: String,
    val suffix: String,
    val visibility: Boolean,
    val width: Int
)