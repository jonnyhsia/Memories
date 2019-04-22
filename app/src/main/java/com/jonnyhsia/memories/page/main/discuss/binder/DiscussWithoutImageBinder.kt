package com.jonnyhsia.memories.page.main.discuss.binder

import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.asAvatar
import com.arch.jonnyhsia.foundation.ext.load
import com.arch.jonnyhsia.foundation.ext.setTextFuture
import com.arch.jonnyhsia.memories.model.story.bean.DiscussDisplayModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_discuss_without_image.*

class DiscussWithoutImageBinder : ItemBinder<DiscussDisplayModel>() {

    override val itemViewRes: Int
        get() = R.layout.item_discuss_without_image

    override fun onBindViewHolder(holder: ExtViewHolder, item: DiscussDisplayModel) {
        with(holder) {
            imgGroupAvatar.load(item.group.avatar) {
                placeholder(R.drawable.placeholder_oval)
                asAvatar()
            }
            tvGroupName.text = item.group.name
            tvDiscussTitle.setTextFuture(item.title)
            tvDiscussContent.setTextFuture(item.description)
            tvDiscussInfo.text = "${item.comments}人讨论 · ${item.updateTime}"

            itemView.setOnClickListener {

            }
        }
    }
}