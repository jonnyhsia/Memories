package com.jonnyhsia.memories.page.main.discuss.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arch.jonnyhsia.memories.model.group.bean.GroupListModel
import com.arch.jonnyhsia.ui.ext.asHorizontalList
import com.arch.jonnyhsia.ui.recyclerview.XMultiAdapter
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_group_list.*
import me.drakeet.multitype.register

class GroupListBinder : ItemBinder<GroupListModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_group_list

    override fun onViewHolderCreated(holder: ExtViewHolder) {
        super.onViewHolderCreated(holder)
        holder.recyclerGroup.asHorizontalList()
        holder.recyclerGroup.adapter = XMultiAdapter().apply {
            register(SimpleGroupBinder())
        }
    }

    override fun onBindViewHolder(holder: ExtViewHolder, item: GroupListModel) {
        with(holder) {
            if (tabLayout.childCount != 0) {
                return
            }
            tabLayout.setTabs(item.tags.map { it.name })
            (recyclerGroup.adapter as XMultiAdapter).setModels(item.groupOfTagList[0])
        }
    }
}