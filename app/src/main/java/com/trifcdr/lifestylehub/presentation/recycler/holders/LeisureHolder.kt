package com.trifcdr.lifestylehub.presentation.recycler.holders

import android.view.View
import androidx.databinding.ViewDataBinding
import com.trifcdr.lifestylehub.R
import com.trifcdr.lifestylehub.databinding.ItemLeisureBinding
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.LeisureItem
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener
import com.trifcdr.lifestylehub.presentation.recycler.visitor.ViewHolderVisitor

/**
 * Created by trifcdr.
 */
class LeisureHolder: ViewHolderVisitor {

    override val layout = R.layout.item_leisure
    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: CustomItemClickListener?
    ) {
        with(binding as ItemLeisureBinding){
            leisure = item as LeisureItem
            lName.text = item.name
            lDate.text = item.date
            lNotes.text = item.note
            cardView.setOnClickListener {
                if (noteLlc.visibility == View.GONE) noteLlc.visibility = View.VISIBLE
                else noteLlc.visibility = View.GONE
            }
            deleteBtn.setOnClickListener {
                clickListener?.onClick(item.id, item.leisureId, 0)

            }
            item.venue?.let {venue ->
                moreBtn.visibility = View.VISIBLE
                moreBtn.setOnClickListener {
                    clickListener?.onClick(venue, item.leisureId, 1)
                }
            }

        }
    }

    override fun acceptBinding(item: Any): Boolean = item is LeisureItem


}