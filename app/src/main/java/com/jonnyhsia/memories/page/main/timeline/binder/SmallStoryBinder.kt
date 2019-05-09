package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.asRounded
import com.jonnyhsia.appcore.ext.dp
import com.jonnyhsia.appcore.ext.load
import com.jonnyhsia.appcore.ext.setTextFuture
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