package com.jonnyhsia.memories.page.main.discuss.binder

import android.widget.TextView
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.memories.R

class HeaderBinder : ItemBinder<String>() {
    override val itemViewRes: Int
        get() = R.layout.item_common_header

    override fun onBindViewHolder(holder: ExtViewHolder, item: String) {
        (holder.itemView as TextView).text = item
    }
}