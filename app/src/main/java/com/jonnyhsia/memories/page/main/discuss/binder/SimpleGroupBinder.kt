package com.jonnyhsia.memories.page.main.discuss.binder

import com.arch.jonnyhsia.memories.model.story.bean.GroupDisplayModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.asRounded
import com.jonnyhsia.appcore.ext.dp
import com.jonnyhsia.appcore.ext.load
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