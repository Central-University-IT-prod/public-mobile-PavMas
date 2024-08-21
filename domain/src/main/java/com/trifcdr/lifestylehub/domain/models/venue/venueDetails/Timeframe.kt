package com.trifcdr.lifestylehub.domain.models.venue.venueDetails



data class Timeframe(
    val days: String,
    val includesToday: Boolean,
    val `open`: List<Open>
)