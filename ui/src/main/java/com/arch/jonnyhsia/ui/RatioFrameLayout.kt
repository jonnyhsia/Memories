package com.arch.jonnyhsia.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class RatioFrameLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val aspectRatio: Float

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout)
        // 默认选中的 tab 索引
        aspectRatio = a.getFloat(R.styleable.RatioFrameLayout_x_ratio, 1f)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        when {
            widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY -> {
                val width = MeasureSpec.getSize(widthMeasureSpec)
                val height = (width / aspectRatio).toInt()
                super.onMeasure(
                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
            }
            heightMode == MeasureSpec.EXACTLY && widthMode != MeasureSpec.EXACTLY -> {
                val height = MeasureSpec.getSize(heightMeasureSpec)
                val width = (aspectRatio * height).toInt()
                super.onMeasure(
                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
            }
            else -> {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        }
    }
}