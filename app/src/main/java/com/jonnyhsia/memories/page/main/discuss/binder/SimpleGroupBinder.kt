package com.jonnyhsia.memories.page.main.discuss.binder

import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.asRounded
import com.arch.jonnyhsia.foundation.ext.dp
import com.arch.jonnyhsia.foundation.ext.load
import com.arch.jonnyhsia.memories.model.story.bean.GroupDisplayModel
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_simple_group.*

class SimpleGroupBinder : ItemBinder<GroupDisplayModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_simple_group

    override fun onBindViewHolder(holder: ExtViewHolder, item: GroupDisplayModel) {
        with(holder) {
            imgGroupAvatar.load(item.avatar) {
                asRounded(10.dp)
                placeholder(R.drawable.placeholder)
            }
            tvGroupName.text = item.name
        }
    }
}