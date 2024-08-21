package com.trifcdr.lifestylehub.presentation.recycler.itemModels

/**
 * Created by trifcdr.
 */
data class LeisureItem(
    override val id: String = "leisure",
    val leisureId: Long? = null,
    val venue: String? = null,
    val name: String,
    val date: String,
    val note: String? = null
) : StringId