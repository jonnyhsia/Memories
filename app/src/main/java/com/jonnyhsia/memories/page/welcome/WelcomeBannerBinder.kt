package com.jonnyhsia.memories.page.welcome

import android.widget.ImageView
import android.widget.Toast
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

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "这里点了也没用", Toast.LENGTH_SHORT).show()
        }
    }
}