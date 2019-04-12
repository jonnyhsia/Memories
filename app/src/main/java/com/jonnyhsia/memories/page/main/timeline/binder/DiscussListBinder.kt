package com.jonnyhsia.memories.page.main.timeline.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.dp
import com.arch.jonnyhsia.memories.model.story.bean.DiscussDisplayModel
import com.arch.jonnyhsia.memories.model.story.bean.DiscussListModel
import com.arch.jonnyhsia.ui.ext.asGridList
import com.arch.jonnyhsia.ui.recyclerview.XMultiAdapter
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.ui.recyclerview.ReadMoreBinder
import com.jonnyhsia.memories.ui.recyclerview.ReadMoreModel
import kotlinx.android.synthetic.main.item_discuss_list.*
import me.drakeet.multitype.register

class DiscussListBinder : ItemBinder<DiscussListModel>() {
    override val itemViewRes: Int
        get() = R.layout.item_discuss_list

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ExtViewHolder {
        val holder = super.onCreateViewHolder(inflater, parent)
        holder.recyclerDiscuss.adapter = XMultiAdapter().apply {
            register(ReadMoreBinder())
            register(DiscussDisplayModel::class)
                    .to(DiscussBigBinder(), DiscussSmallBinder())
                    .withLinker { position, _ ->
                        if (position == 0) 0 else 1
                    }
        }
        holder.recyclerDiscuss.asGridList(spanCount = 2, orientation = RecyclerView.HORIZONTAL) {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0 || position == itemCount - 1) 2 else 1
                }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ExtViewHolder, item: DiscussListModel) {
        with(holder) {
            tvDiscussTitle.text = item.title
            (recyclerDiscuss.adapter as XMultiAdapter).items = item.list + ReadMoreModel(additionMarginStart = 12.dp)
        }
    }
}