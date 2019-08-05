package com.jonnyhsia.memories.page.main.timeline.binder

import com.arch.jonnyhsia.memories.model.story.bean.StoryHeaderModel
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.appcore.ext.asHorizontalList
import com.jonnyhsia.appcore.ui.XMultiAdapter
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_story_header.*
import me.drakeet.multitype.register

class StoryHeaderBinder : ItemBinder<StoryHeaderModel>() {

    override val itemViewRes: Int
        get() = R.layout.item_story_header

    override fun onViewHolderCreated(holder: ExtViewHolder) {
        super.onViewHolderCreated(holder)

        val adapter = XMultiAdapter()
        adapter.register(StoryTagBinder())
        holder.recyclerTags.adapter = adapter
        holder.recyclerTags.asHorizontalList()
    }

    override fun onBindViewHolder(holder: ExtViewHolder, item: StoryHeaderModel) {
        with(holder) {
            tvTitle.text = item.title
            (recyclerTags.adapter as XMultiAdapter).items = item.tags
        }
    }
}