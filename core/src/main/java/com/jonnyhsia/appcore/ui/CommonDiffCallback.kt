package com.jonnyhsia.appcore.ui

import androidx.recyclerview.widget.DiffUtil

open class CommonDiffCallback(
        protected var old: List<*>? = null,
        protected var new: List<*>? = null
) : DiffUtil.Callback() {

    override fun getOldListSize() = old?.size ?: 0

    override fun getNewListSize() = new?.size ?: 0
    /**
     * 是否为同一个 item
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old?.getOrNull(oldItemPosition) === new?.getOrNull(newItemPosition)
    }

    /**
     * item 内容是否改变
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old?.getOrNull(oldItemPosition).hashCode() ==
                new?.getOrNull(newItemPosition).hashCode()
    }

    fun changeData(oldData: List<*>, newData: List<*>): CommonDiffCallback {
        old = oldData
        new = newData
        return this
    }

    internal fun getNewList(): List<*>? {
        return new
    }
}