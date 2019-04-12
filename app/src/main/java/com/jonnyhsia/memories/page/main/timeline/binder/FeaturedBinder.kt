package com.jonnyhsia.memories.page.main.timeline.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arch.jonnyhsia.foundation.component.ExtViewHolder
import com.arch.jonnyhsia.foundation.component.ItemBinder
import com.arch.jonnyhsia.foundation.ext.dp
import com.arch.jonnyhsia.memories.model.story.bean.FeaturedStoriesModel
import com.arch.jonnyhsia.ui.ext.asHorizontalList
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.ui.recyclerview.ReadMoreBinder
import com.jonnyhsia.memories.ui.recyclerview.ReadMoreModel
import kotlinx.android.synthetic.main.item_featured.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register

class FeaturedBinder : ItemBinder<FeaturedStoriesModel>() {

    override val itemViewRes: Int
        get() = R.layout.item_featured

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ExtViewHolder {
        val holder = super.onCreateViewHolder(inflater, parent)
        val adapterOfHolder = MultiTypeAdapter()
        adapterOfHolder.register(SmallStoryBinder())
        adapterOfHolder.register(ReadMoreBinder())
        holder.recyclerFeatured.adapter = adapterOfHolder

        return holder
    }

    override fun onBindViewHolder(holder: ExtViewHolder, item: FeaturedStoriesModel) {
        with(holder) {
            recyclerFeatured.asHorizontalList()
            (recyclerFeatured.adapter as MultiTypeAdapter).items = item.list + ReadMoreModel(additionMarginBottom = 64.dp)
        }
    }
}