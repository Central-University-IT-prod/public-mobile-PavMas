package com.trifcdr.lifestylehub.domain.models.venue.venueRecommends



data class Group(
    val results: List<Result>,
    val totalResults: Int? = null
)