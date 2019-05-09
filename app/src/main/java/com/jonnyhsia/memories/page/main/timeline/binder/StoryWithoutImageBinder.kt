package com.jonnyhsia.memories.page.main.timeline.binder

import android.view.Gravity
import android.view.MotionEvent
import androidx.core.view.isVisible
import com.arch.jonnyhsia.memories.model.story.bean.StoryDisplayModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.asAvatar
import com.jonnyhsia.appcore.ext.loadDrawable
import com.jonnyhsia.appcore.ext.setTextFuture
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.application
import kotlinx.android.synthetic.main.item_story_no_image.*

class StoryWithoutImageBinder : ItemBinder<StoryDisplayModel>() {

    override val itemViewRes: Int
        get() = R.layout.item_story_no_image

    private var touchDownTime = 0L

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
                tvStoryContent.setTextFuture(item.summary)
            } else {
                tvStoryContent.isVisible = false
            }

            itemView.setOnClickListener {

            }

            itemView.setOnTouchListener { _, event ->
                when {
                    event.action == MotionEvent.ACTION_DOWN -> {
                        touchDownTime = System.currentTimeMillis()
                    }
                    event.action == MotionEvent.ACTION_UP && touchDownTime != 0L -> {
                        val downTime = System.currentTimeMillis() - touchDownTime
                        if (downTime > 3000L) {
                            // TODO: 改到 vm 中
                            application.checkTrophy(id = 2)
                        }
                        touchDownTime = 0L
                    }
                }
                false
            }
        }
    }
}