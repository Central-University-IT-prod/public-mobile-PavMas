package com.trifcdr.lifestylehub.domain.models.venue.venueDetails



data class VenueDetails(
    val bestPhoto: BestPhoto,
    val categories: List<CategoryDetails>,
    val contact: Contact,
    val hereNow: HereNow,
    val hours: Hours,
    val id: String,
    val likes: Likes,
    val listed: Listed,
    val locationDetails: LocationDetails,
    val name: String,
    val reasons: Reasons,
    val url: String
)