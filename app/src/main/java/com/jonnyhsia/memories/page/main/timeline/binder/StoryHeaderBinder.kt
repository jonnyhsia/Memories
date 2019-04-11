package com.jonnyhsia.memories.page.main.timeline.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.memories.model.story.bean.StoryHeaderModel
import com.arch.jonnyhsia.ui.ext.asHorizontalList
import com.arch.jonnyhsia.ui.recyclerview.XMultiAdapter
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_story_header.*
import me.drakeet.multitype.register

class StoryHeaderBinder : ItemBinder<StoryHeaderModel>() {

    override val itemViewRes: Int
        get() = R.layout.item_story_header

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ExtViewHolder {
        val holder = super.onCreateViewHolder(inflater, parent)
        val adapter = XMultiAdapter()
        adapter.register(StoryTagBinder())
        holder.recyclerTags.adapter = adapter
        holder.recyclerTags.asHorizontalList()
        return holder
    }

    override fun onBindViewHolder(holder: ExtViewHolder, item: StoryHeaderModel) {
        with(holder) {
            tvTitle.text = item.title
            (recyclerTags.adapter as XMultiAdapter).items = item.tags
        }
    }
}