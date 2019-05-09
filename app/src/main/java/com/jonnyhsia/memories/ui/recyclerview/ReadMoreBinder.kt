package com.jonnyhsia.memories.ui.recyclerview

import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.memories.R

class ReadMoreModel(
        val additionMarginStart: Int = 0,
        val additionMarginEnd: Int = 0,
        val additionMarginTop: Int = 0,
        val additionMarginBottom: Int = 0
)

class ReadMoreBinder : ItemBinder<ReadMoreModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_read_more

    override fun onBindViewHolder(holder: ExtViewHolder, item: ReadMoreModel) {
        if (item.additionMarginStart != 0 || item.additionMarginEnd != 0 ||
                item.additionMarginTop != 0 || item.additionMarginBottom != 0) {
            holder.itemView.updateLayoutParams<RecyclerView.LayoutParams> {
                setMargins(item.additionMarginStart, item.additionMarginTop, item.additionMarginEnd, item.additionMarginBottom)
            }
        }
    }
}