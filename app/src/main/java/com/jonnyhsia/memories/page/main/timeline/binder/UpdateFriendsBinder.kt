package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.memories.model.story.bean.UpdateFriendsModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.asHorizontalList
import com.jonnyhsia.appcore.ui.XMultiAdapter
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_update_friends.*
import me.drakeet.multitype.register

class UpdateFriendsBinder : ItemBinder<UpdateFriendsModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_update_friends

    override fun onViewHolderCreated(holder: ExtViewHolder) {
        super.onViewHolderCreated(holder)
        val adapter = XMultiAdapter()
        adapter.register(FriendBinder())
        holder.recyclerFriends.asHorizontalList()
        holder.recyclerFriends.adapter = adapter
    }

    override fun onBindViewHolder(holder: ExtViewHolder, item: UpdateFriendsModel) {
        with(holder) {
            tvTitle.text = item.title
            (recyclerFriends.adapter as XMultiAdapter).items = item.friends
        }
    }
}