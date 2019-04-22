package com.jonnyhsia.memories.page.main.discuss.binder

import android.graphics.Color
import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.*
import com.arch.jonnyhsia.memories.model.story.bean.DiscussDisplayModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_discuss_with_image.*

class DiscussWithImageBinder : ItemBinder<DiscussDisplayModel>() {

    override val itemViewRes: Int
        get() = R.layout.item_discuss_with_image

    override fun onBindViewHolder(holder: ExtViewHolder, item: DiscussDisplayModel) {
        with(holder) {
            imgDiscussImage.foreground.setTint(Color.parseColor(item.tint))
            imgDiscussImage.load(item.image) {
                placeholder(R.drawable.placeholder_dark)
                asRounded(10.dp)
            }
            imgGroupAvatar.load(item.group.avatar) {
                placeholder(R.drawable.placeholder_oval)
                asAvatar()
            }
            tvGroupName.text = item.group.name
            tvDiscussTitle.text = item.title
            tvDiscussContent.setTextFuture(item.description)
            tvDiscussInfo.text = "${item.comments}人讨论 · ${item.updateTime}"

            itemView.setOnClickListener {

            }
        }
    }
}