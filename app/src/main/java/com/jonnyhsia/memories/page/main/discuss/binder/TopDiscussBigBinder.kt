package com.jonnyhsia.memories.page.main.discuss.binder

import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.*
import com.arch.jonnyhsia.memories.model.story.bean.TopDiscussModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_top_discuss_big.*

class TopDiscussBigBinder : ItemBinder<TopDiscussModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_top_discuss_big

    override fun onBindViewHolder(holder: ExtViewHolder, item: TopDiscussModel) {
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
