package com.jonnyhsia.appcore.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import me.drakeet.multitype.ItemViewBinder

abstract class ItemBinder<T> : ItemViewBinder<T, ExtViewHolder>() {

    abstract val itemViewRes: Int

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ExtViewHolder {
        val view = inflater.inflate(itemViewRes, parent, false)
        return ExtViewHolder(view)
    }

    fun RecyclerView.ViewHolder.getItem(): T {
        return adapter.items.get(adapterPosition) as T
    }
}

class ExtViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer