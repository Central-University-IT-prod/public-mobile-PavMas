package com.trifcdr.lifestylehub.domain.models.venue.venueRecommends


data class VenueData(
    val results: List<Result>? = null,
    val totalResults: Int
)