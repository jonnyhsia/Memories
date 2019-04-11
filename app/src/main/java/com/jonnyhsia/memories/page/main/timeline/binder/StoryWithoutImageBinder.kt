package com.jonnyhsia.memories.page.main.timeline.binder

import android.view.Gravity
import androidx.core.view.isVisible
import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.asAvatar
import com.arch.jonnyhsia.foundation.ext.loadDrawable
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_story_no_image.*

class StoryWithoutImageBinder : ItemBinder<StoryDisplayModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_story_no_image

    override fun onBindViewHolder(holder: ExtViewHolder, item: StoryDisplayModel) {
        with(holder) {
            tvAuthor.text = item.author.nickname
            tvAuthor.loadDrawable(item.author.avatar, Gravity.START) {
                asAvatar()
            }

            tvDate.text = item.dateText

            var summaryIsDone = false
            when {
                item.title.isNotBlank() -> {
                    tvStoryTitle.isVisible = true
                    tvStoryTitle.text = item.title
                }
                item.summary.length < 80 -> {
                    tvStoryTitle.isVisible = true
                    tvStoryTitle.text = item.summary
                    summaryIsDone = true
                }
                else -> tvStoryTitle.isVisible = false
            }

            if (!summaryIsDone && item.summary.isNotBlank()) {
                tvStoryContent.text = item.summary
            } else {
                tvStoryContent.isVisible = false
            }

            itemView.setOnClickListener {

            }
        }
    }
}