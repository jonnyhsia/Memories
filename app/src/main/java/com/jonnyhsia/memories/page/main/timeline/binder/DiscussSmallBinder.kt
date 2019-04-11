package com.jonnyhsia.memories.page.main.timeline.binder

import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.asRounded
import com.arch.jonnyhsia.foundation.ext.dp
import com.arch.jonnyhsia.foundation.ext.load
import com.arch.jonnyhsia.memories.model.story.bean.DiscussDisplayModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_discuss_small.*

class DiscussSmallBinder : ItemBinder<DiscussDisplayModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_discuss_small

    override fun onBindViewHolder(holder: ExtViewHolder, item: DiscussDisplayModel) {
        with(holder) {
            imgDiscussImage.load(item.image) {
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