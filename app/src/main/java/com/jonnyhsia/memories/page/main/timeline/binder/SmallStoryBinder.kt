package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.asRounded
import com.arch.jonnyhsia.foundation.ext.dp
import com.arch.jonnyhsia.foundation.ext.load
import com.arch.jonnyhsia.foundation.ext.setTextFuture
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_small_story.*

class SmallStoryBinder : ItemBinder<StoryDisplayModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_small_story

    override fun onBindViewHolder(holder: ExtViewHolder, item: StoryDisplayModel) {
        with(holder) {
            imgStory.load(item.image) {
                placeholder(R.drawable.placeholder_dark)
                asRounded(10.dp)
            }
            tvStoryTag.text = item.tags.firstOrNull()

            val text = when {
                item.title.isBlank() -> item.summary
                item.summary.isBlank() -> item.title
                else -> "${item.title}\n${item.summary}"
            }
            tvStoryContent.setTextFuture(text)
        }
    }
}