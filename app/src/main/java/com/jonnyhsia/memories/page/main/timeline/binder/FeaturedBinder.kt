package com.jonnyhsia.memories.page.main.timeline.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arch.jonnyhsia.memories.model.story.bean.FeaturedStoriesModel
import com.arch.jonnyhsia.ui.ext.asHorizontalList
import com.jonnyhsia.appcore.component.ExtViewHolder
import com.jonnyhsia.appcore.component.ItemBinder
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.item_featured.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register

class FeaturedBinder : ItemBinder<FeaturedStoriesModel>() {

    override val itemViewRes: Int
        get() = R.layout.item_featured

    override fun onViewHolderCreated(holder: ExtViewHolder) {
        super.onViewHolderCreated(holder)
        val adapterOfHolder = MultiTypeAdapter()
        adapterOfHolder.register(SmallStoryBinder())
        // adapterOfHolder.register(ReadMoreBinder())
        holder.recyclerFeatured.adapter = adapterOfHolder
    }
    override fun onBindViewHolder(holder: ExtViewHolder, item: FeaturedStoriesModel) {
        with(holder) {
            recyclerFeatured.asHorizontalList()
            (recyclerFeatured.adapter as MultiTypeAdapter).items = item.list
        }
    }
}