package com.jonnyhsia.appcore.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import me.drakeet.multitype.ItemViewBinder

abstract class ItemBinder<T> : ItemViewBinder<T, ExtViewHolder>() {

    abstract val itemViewRes: Int

    final override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ExtViewHolder {
        val view = inflater.inflate(itemViewRes, parent, false)
        val holder = ExtViewHolder(view)
        onViewHolderCreated(holder)
        return holder
    }

    open fun onViewHolderCreated(holder: ExtViewHolder) {
    }


    val RecyclerView.ViewHolder.currentItem: T
        get() {
            return adapter.items[adapterPosition] as T
        }
}

class ExtViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer