package com.jonnyhsia.memories.page.main.discuss.binder

import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.asRounded
import com.arch.jonnyhsia.foundation.ext.dp
import com.arch.jonnyhsia.foundation.ext.load
import com.arch.jonnyhsia.memories.model.story.bean.TopDiscussModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_top_discuss_small.*

class TopDiscussSmallBinder : ItemBinder<TopDiscussModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_top_discuss_small

    override fun onBindViewHolder(holder: ExtViewHolder, item: TopDiscussModel) {
        with(holder) {
            imgDiscussImage.load(item.image) {
                placeholder(R.drawable.placeholder_dark)
                asRounded(10.dp)
            }
            tvDiscussTitle.text = item.title

            if (adapterPosition % 2 == 1) {
                tvDiscussTitle.updateLayoutParams<FrameLayout.LayoutParams> {
                    setMargins(marginStart, topMargin, marginEnd, 5.dp)
                }
            } else {
                imgDiscussImage.updateLayoutParams<FrameLayout.LayoutParams> {
                    setMargins(marginStart, 5.dp, marginEnd, bottomMargin)
                }
            }
        }
    }
}