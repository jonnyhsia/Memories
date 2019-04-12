package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.asRounded
import com.arch.jonnyhsia.foundation.ext.dp
import com.arch.jonnyhsia.foundation.ext.load
import com.arch.jonnyhsia.memories.model.story.bean.SimpleUserDisplayModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_friend.*

class FriendBinder : ItemBinder<SimpleUserDisplayModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_friend

    override fun onBindViewHolder(holder: ExtViewHolder, item: SimpleUserDisplayModel) {
        with(holder) {
            tvFriendName.text = item.nickname
            imgAvatar.load(item.avatar) {
                placeholder(R.drawable.placeholder_dark)
                asRounded(10.dp)
            }
        }
    }
}