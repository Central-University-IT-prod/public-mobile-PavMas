package com.trifcdr.lifestylehub.domain.models.venue.venueRecommends



data class Result(
    val id: String,
    val photo: Photo? = null,
    val venue: Venue? = null
)