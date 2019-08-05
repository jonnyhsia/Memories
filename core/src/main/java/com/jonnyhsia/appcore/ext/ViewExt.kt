package com.jonnyhsia.appcore.ext

import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.appbar.AppBarLayout

fun RecyclerView.asVerticalList() {
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.asFlexbox() {
    setHasFixedSize(true)
    layoutManager = FlexboxLayoutManager(context)
}

fun RecyclerView.asHorizontalList(reverseLayout: Boolean = false) {
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, reverseLayout)
}

inline fun RecyclerView.asGridList(
        spanCount: Int,
        orientation: Int = RecyclerView.VERTICAL,
        reverseLayout: Boolean = false,
        block: GridLayoutManager.() -> Unit = {}
) {
    setHasFixedSize(true)
    layoutManager = GridLayoutManager(context, spanCount, orientation, reverseLayout).apply(block)
}

fun RecyclerView.asStaggerGridList(
        spanCount: Int,
        orientation: Int = RecyclerView.VERTICAL
) {
    setHasFixedSize(true)
    layoutManager = StaggeredGridLayoutManager(spanCount, orientation)
}

val RecyclerView.LayoutManager.firstVisibleItemPosition: Int
    get() {
        return when (this) {
            is LinearLayoutManager -> findFirstVisibleItemPosition()
            is StaggeredGridLayoutManager -> {
                val positions = IntArray(spanCount) { 0 }
                findFirstVisibleItemPositions(positions)
                positions.max() ?: 0
            }
            else -> throw RuntimeException("不支持的 Layout Manager")
        }
    }

val RecyclerView.LayoutManager.lastVisibleItemPosition: Int
    get() {
        return when (this) {
            is LinearLayoutManager -> findLastVisibleItemPosition()
            is StaggeredGridLayoutManager -> {
                val positions = IntArray(spanCount) { 0 }
                findLastVisibleItemPositions(positions)
                positions.max() ?: 0
            }
            else -> throw RuntimeException("不支持的 Layout Manager")
        }
    }

val RecyclerView.LayoutManager.firstCompletelyVisibleItemPosition: Int
    get() {
        return when (this) {
            is LinearLayoutManager -> findFirstCompletelyVisibleItemPosition()
            is StaggeredGridLayoutManager -> {
                val positions = IntArray(spanCount) { 0 }
                findFirstCompletelyVisibleItemPositions(positions)
                positions.max() ?: 0
            }
            else -> throw RuntimeException("不支持的 Layout Manager")
        }
    }

val RecyclerView.LayoutManager.lastCompletelyVisibleItemPosition: Int
    get() {
        return when (this) {
            is LinearLayoutManager -> findLastCompletelyVisibleItemPosition()
            is StaggeredGridLayoutManager -> {
                val positions = IntArray(spanCount) { 0 }
                findLastCompletelyVisibleItemPositions(positions)
                positions.max() ?: 0
            }
            else -> throw RuntimeException("不支持的 Layout Manager")
        }
    }

fun RecyclerView.scrollToPositionWithOffset(position: Int, offset: Int = 0) {
    val manager = layoutManager
    when (manager) {
        is LinearLayoutManager -> manager.scrollToPositionWithOffset(position, offset)
        is StaggeredGridLayoutManager -> {
            manager.scrollToPositionWithOffset(position, offset)
        }
        else -> throw RuntimeException("不支持的 Layout Manager")
    }
}

var AppBarLayout.isFullyExpanded: Boolean
    get() {
        return height - bottom == 0
    }
    set(value) {
        setExpanded(value)
    }

/**
 * @param offset AppBarLayout 完全收拢仍然露出的大小 (例TabLayout)
 */
fun AppBarLayout.isExpanded(offset: Int): Boolean {
    return (bottom - offset) > 0
}

var View.tooltipTextCompat: CharSequence?
    set(value) {
        TooltipCompat.setTooltipText(this, value)
    }
    get() {
        throw Exception()
    }
