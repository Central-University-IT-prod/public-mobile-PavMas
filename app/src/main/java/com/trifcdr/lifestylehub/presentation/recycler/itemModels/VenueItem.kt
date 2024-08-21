package com.trifcdr.lifestylehub.presentation.recycler.itemModels

/**
 * Created by trifcdr.
 */
data class VenueItem(
    override val id: String = "venue",
    val venueId: String,
    val image: String?,
    val name: String,
    val category: String,
    val address: String
) : StringId