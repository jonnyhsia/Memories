package com.jonnyhsia.memories.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FabScrollAwareBehavior : FloatingActionButton.Behavior {

    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super()

    override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: FloatingActionButton,
            directTargetChild: View,
            target: View,
            axes: Int,
            type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        if (dyConsumed > 0 && !child.isOrWillBeHidden) {
            child.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                @SuppressLint("RestrictedApi")
                override fun onHidden(fab: FloatingActionButton) {
                    fab.visibility = View.INVISIBLE
                }
            })
        } else if (dyConsumed < 0 && !child.isOrWillBeShown) {
            child.show()
        }
    }
}