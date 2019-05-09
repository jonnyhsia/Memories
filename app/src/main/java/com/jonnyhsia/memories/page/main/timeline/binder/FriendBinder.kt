package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.memories.model.story.bean.SimpleUserDisplayModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.asRounded
import com.jonnyhsia.appcore.ext.dp
import com.jonnyhsia.appcore.ext.load
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