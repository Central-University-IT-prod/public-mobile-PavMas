package com.trifcdr.lifestylehub.domain.models.venue.venueRecommends



data class Location(
    val address: String? = null,
    val distance: Int? = null,
    val formattedAddress: List<String>,
)