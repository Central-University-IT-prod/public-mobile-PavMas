package com.trifcdr.lifestylehub.presentation.recycler.itemModels

/**
 * Created by trifcdr.
 */
data class PhotoItem(
    override val id: String = "photo",
    val photo: String
) : StringId