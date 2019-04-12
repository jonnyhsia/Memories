package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.*
import com.arch.jonnyhsia.memories.model.story.bean.DiscussDisplayModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_discuss_big.*

class DiscussBigBinder : ItemBinder<DiscussDisplayModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_discuss_big

    override fun onBindViewHolder(holder: ExtViewHolder, item: DiscussDisplayModel) {
        with(holder) {
            imgDiscuss.load(item.image) {
                placeholder(R.drawable.placeholder_dark)
                asRounded(10.dp)
            }
            tvDiscussTitle.text = item.title.spannableBuilder() + "\n" +
                    item.description.spannable { setSize(13) }
            tvDiscussMeta.text = item.meta
        }
    }
}
