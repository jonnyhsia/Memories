package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.asRounded
import com.arch.jonnyhsia.foundation.ext.dp
import com.arch.jonnyhsia.foundation.ext.load
import com.arch.jonnyhsia.memories.model.story.bean.TagModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_story_tag.*

class StoryTagBinder : ItemBinder<TagModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_story_tag

    override fun onBindViewHolder(holder: ExtViewHolder, item: TagModel) {
        with(holder) {
            imgTag.load(item.icon) {
                asRounded(10.dp)
            }
            tvTagName.text = item.text
        }
    }
}