package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.memories.model.story.bean.TagModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.asRounded
import com.jonnyhsia.appcore.ext.dp
import com.jonnyhsia.appcore.ext.load
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_story_tag.*

class StoryTagBinder : ItemBinder<TagModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_story_tag

    override fun onBindViewHolder(holder: ExtViewHolder, item: TagModel) {
        with(holder) {
            imgTag.load(item.icon) {
                placeholder(R.drawable.placeholder_dark)
                asRounded(10.dp)
            }
            tvTagName.text = item.text
        }
    }
}