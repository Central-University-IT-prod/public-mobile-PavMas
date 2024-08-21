package com.trifcdr.lifestylehub.domain.models.venue.venueRecommends



data class Venue(
    val categories: List<Category>,
    val id: String,
    val location: Location,
    val name: String? = null
)