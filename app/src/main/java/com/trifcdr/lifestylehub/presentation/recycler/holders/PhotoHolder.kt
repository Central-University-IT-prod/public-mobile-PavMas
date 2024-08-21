package com.trifcdr.lifestylehub.presentation.recycler.holders

import androidx.databinding.ViewDataBinding
import com.squareup.picasso.Picasso
import com.trifcdr.lifestylehub.R
import com.trifcdr.lifestylehub.databinding.ItemPhotoBinding
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.PhotoItem
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener
import com.trifcdr.lifestylehub.presentation.recycler.visitor.ViewHolderVisitor

/**
 * Created by trifcdr.
 */
class PhotoHolder : ViewHolderVisitor {
    override val layout = R.layout.item_photo

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: CustomItemClickListener?
    ) {
        with(binding as ItemPhotoBinding){
            photo = item as PhotoItem
            Picasso
                .get()
                .load(item.photo)
                .into(venuePhotoDetails)

        }

    }

    override fun acceptBinding(item: Any): Boolean = item is PhotoItem

}