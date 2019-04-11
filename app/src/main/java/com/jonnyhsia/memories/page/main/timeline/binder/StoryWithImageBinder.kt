package com.jonnyhsia.memories.page.main.timeline.binder

import android.view.Gravity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.*
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_story_with_image.*

class StoryWithImageBinder : ItemBinder<StoryDisplayModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_story_with_image

    override fun onBindViewHolder(holder: ExtViewHolder, item: StoryDisplayModel) {
        with(holder) {
            tvAuthor.text = item.author.nickname
            tvAuthor.loadDrawable(item.author.avatar, Gravity.START) {
                asAvatar()
            }

            imgStory.updateLayoutParams<ConstraintLayout.LayoutParams> {
                height = ((displayWidth - 40.dp) / item.imageRatio).toInt()
            }
            imgStory.load(item.image) {
                asRounded(leftTop = 10f.dp, rightTop = 10f.dp)
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

