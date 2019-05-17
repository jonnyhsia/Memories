package com.arch.jonnyhsia.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageView

private val DRAWABLE_STATE_CHECKED = intArrayOf(android.R.attr.state_checked)

typealias OnCheckedChangeListener = (view: ImageToggle, checked: Boolean) -> Unit

class ImageToggle @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), Checkable {

    private var isChecked: Boolean
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    init {
        isClickable = true
        isFocusable = true
        isDuplicateParentStateEnabled = true
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.ImageToggle)
        isChecked = a.getBoolean(R.styleable.ImageToggle_android_checked, false)
        a.recycle()
    }

    override fun isChecked(): Boolean {
        return isChecked
    }

    override fun toggle() {
        setChecked(!isChecked)
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        onCheckedChangeListener = listener
    }

    override fun performClick(): Boolean {
        val b = super.performClick()
        toggle()
        return b
    }

    override fun setChecked(checked: Boolean) {
        if (checked == isChecked) {
            return
        }

        isChecked = checked
        onCheckedChangeListener?.invoke(this, isChecked)
        refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        return if (isChecked) {
            View.mergeDrawableStates(super.onCreateDrawableState(extraSpace + DRAWABLE_STATE_CHECKED.size), DRAWABLE_STATE_CHECKED)
        } else {
            super.onCreateDrawableState(extraSpace)
        }
    }
}