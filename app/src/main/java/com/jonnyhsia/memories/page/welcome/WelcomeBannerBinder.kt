package com.jonnyhsia.memories.page.welcome

import android.widget.ImageView
import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.bumptech.glide.Glide
import com.jonnyhsia.memories.R

class WelcomeBannerBinder : ItemBinder<Int>() {

    override val itemViewRes: Int
        get() = R.layout.item_welcome_banner

    override fun onBindViewHolder(holder: ExtViewHolder, item: Int) {
        Glide.with(holder.itemView)
                .load(item)
                .into(holder.itemView as ImageView)
    }
}