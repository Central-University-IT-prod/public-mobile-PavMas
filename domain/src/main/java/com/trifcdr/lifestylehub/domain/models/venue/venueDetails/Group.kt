package com.trifcdr.lifestylehub.domain.models.venue.venueDetails



data class Group(
    val count: Int,
    val itemDetails: List<ItemDetails>,
    val name: String,
    val type: String
)