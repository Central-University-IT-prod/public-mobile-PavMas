package com.trifcdr.lifestylehub.presentation.recycler.holders

import android.view.View
import androidx.databinding.ViewDataBinding
import com.squareup.picasso.Picasso
import com.trifcdr.lifestylehub.R
import com.trifcdr.lifestylehub.databinding.ItemVenueBinding
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.VenueItem
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener
import com.trifcdr.lifestylehub.presentation.recycler.visitor.ViewHolderVisitor

/**
 * Created by trifcdr.
 */
class VenueHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.item_venue

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: CustomItemClickListener?
    ) {
        with(binding as ItemVenueBinding) {
            venue = item as VenueItem
            Picasso.get()
                .load(item.image)
                .into(venuePhoto)
            if (item.image != null){
                phErrorTv.visibility = View.INVISIBLE

            }
            else{
                phErrorTv.visibility = View.VISIBLE
            }
            addressTv.text = item.address
            nameTv.text = item.name
            categoryTv.text = item.category
            binding.root.setOnClickListener{
                clickListener?.onClick(item.venueId)
            }

        }
    }

    override fun acceptBinding(item: Any): Boolean = item is VenueItem
}