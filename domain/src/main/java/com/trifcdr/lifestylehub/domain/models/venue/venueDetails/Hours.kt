package com.trifcdr.lifestylehub.domain.models.venue.venueDetails



data class Hours(
    val isOpen: Boolean,
    val status: String,
    val timeframes: List<Timeframe>
)