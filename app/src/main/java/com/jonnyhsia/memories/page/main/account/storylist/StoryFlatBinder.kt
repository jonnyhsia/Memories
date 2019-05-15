package com.jonnyhsia.memories.page.main.account.storylist

import androidx.core.view.isVisible
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.*
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
        with(holder) {
            val haveTitle = item.title.isNotBlank()
            if (haveTitle) {
                tvStoryTitle.text = item.title
                tvStoryTitle.isVisible = true
                tvStoryContent.textSize = 13f
                tvStoryContent.setTextColor(Colors(R.color.textColorSecondary))
            } else {
                tvStoryTitle.isVisible = false
                tvStoryContent.textSize = 15f
                tvStoryContent.setTextColor(Colors(R.color.textColorPrimary))
            }

            tvStoryContent.setTextFuture(item.summary, autoHide = false)

            imgStoryCover.isVisible = !item.image.isNullOrEmpty()
            if (item.image != null) {
                imgStoryCover.load(item.image) {
                    asRounded(10.dp)
                }
            }

            tvStoryDate.text = "11:29AM, April 8"
        }
    }
}