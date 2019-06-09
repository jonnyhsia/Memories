package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.compass.Compass
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.memories.R

class StartFromDraftBinder : ItemBinder<Any>() {

    override val itemViewRes = R.layout.item_start_from_draft

    override fun onViewHolderCreated(holder: ExtViewHolder) {
        super.onViewHolderCreated(holder)
        holder.itemView.setOnClickListener {
            Compass.navigate("memo://Compose")
                    .go(holder.context)
        }
    }

    override fun onBindViewHolder(holder: ExtViewHolder, item: Any) {
    }
}