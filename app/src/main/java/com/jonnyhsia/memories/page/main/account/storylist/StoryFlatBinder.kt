package com.jonnyhsia.memories.page.main.account.storylist

import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.asRounded
import com.jonnyhsia.appcore.ext.displayText
import com.jonnyhsia.appcore.ext.dp
import com.jonnyhsia.appcore.ext.load
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_story_flat.*

interface StoryFlatBinderDelegate {

    fun onClickStory(story: StoryDisplayModel, position: Int)
}

class StoryFlatBinder(delegate: StoryFlatBinderDelegate) : ItemBinder<StoryDisplayModel>(), StoryFlatBinderDelegate by delegate {

    override val itemViewRes: Int
        get() = R.layout.item_story_flat

    override fun onViewHolderCreated(holder: ExtViewHolder) {
        super.onViewHolderCreated(holder)
        holder.itemView.setOnClickListener {
            onClickStory(holder.currentItem, holder.adapterPosition)
        }
    }

    override fun onBindViewHolder(holder: ExtViewHolder, item: StoryDisplayModel) {
        with(holder){
            tvStoryTitle.displayText = item.title
            tvStoryContent.text = item.summary
            imgStoryCover.load(item.image){
                asRounded(10.dp)
            }
            tvStoryDate.text = "11:29AM, April 8"
        }
    }
}