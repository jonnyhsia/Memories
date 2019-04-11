package com.arch.jonnyhsia.ui.navigation

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.arch.jonnyhsia.ui.R
import com.arch.jonnyhsia.ui.navigation.BottomNavigationBar.LayoutParams.Companion.ANCHOR_BOTTOM
import com.arch.jonnyhsia.ui.navigation.BottomNavigationBar.LayoutParams.Companion.ANCHOR_LEFT
import com.arch.jonnyhsia.ui.navigation.BottomNavigationBar.LayoutParams.Companion.ANCHOR_RIGHT
import com.arch.jonnyhsia.ui.navigation.BottomNavigationBar.LayoutParams.Companion.ANCHOR_TOP

/**
 * Tab 的选中监听. 返回 true 为消费掉此次事件
 */
typealias OnTabSelectListener = (oldPos: Int, pos: Int) -> Boolean

/**
 * Tab 的复选监听
 */
typealias OnTabReselectListener = (pos: Int) -> Unit

class BottomNavigationBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    /** Tab 选中与复选监听 */
    private var onTabSelectedListener: OnTabSelectListener? = null
    private var onTabReselectedListener: OnTabReselectListener? = null

    /** 包含的 tab view 数组 */
    private val bottomTabs = ArrayList<BottomTab>()

    /** 当前已选中的 tab 索引值 */
    private var selectedIndex: Int

    /** tab view 的数量 */
    val tabCount: Int
        get() = bottomTabs.size

    private val selectEffectEnabled: Boolean

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationBar)
        // 默认选中的 tab 索引
        selectedIndex = a.getInt(R.styleable.BottomNavigationBar_x_defaultTab, -1)
        selectEffectEnabled = a.getBoolean(R.styleable.BottomNavigationBar_x_selectEffect, true)
        a.recycle()
        setWillNotDraw(!selectEffectEnabled)
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
    }

    /**
     * 设置 tab 选中监听
     */
    fun setOnTabSelectListener(listener: OnTabSelectListener) {
        onTabSelectedListener = listener
        if (selectedIndex != -1) {
            onTabSelectedListener!!.invoke(-1, selectedIndex)
        }
    }

    /**
     * 设置 tab 复选监听
     */
    fun setOnTabReselectListener(listener: OnTabReselectListener) {
        onTabReselectedListener = listener
    }

    /**
     * 当 tab 被选中时, 由 [BottomTab] 主动调用
     * @param tab 选中的 tab
     */
    internal fun selectedTab(tab: BottomTab) {
        // 选中的 tab 索引
        val index = bottomTabs.indexOf(tab)

        if (selectedIndex != index) {
            // 如果已选中的 index 与当前选中的 index 不同
            // 则置反原先的 tab, 并调用 "选中监听器"
            val handleSelectEvent = onTabSelectedListener?.invoke(selectedIndex, index)
                    ?: false
            if (handleSelectEvent) {
                return
            }

            bottomTabs[selectedIndex].isChecked = false
            // 更新 index
            selectedIndex = index
        } else {
            // 若相同, 则调用复选监听器
            onTabReselectedListener?.invoke(index)
        }
    }

    /**
     * @return 返回对应 index 的 tab
     */
    fun getTabAt(index: Int): BottomTab {
        return bottomTabs[index]
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        // 测量前先清空 tabs 数组 (onMeasure 可以被重复调用)
        bottomTabs.clear()
        // 获取 tab 的总数, 计算 tab 的宽度与高度
        // (在 measure 时, bottomTabs 还没放入 tab, 不能直接取 this.tabCount)
        val tabCount = children.count { it is BottomTab }
        // FIXME: 宽高计算还需要考虑 tab 的 margin
        val tabWidth = (measuredWidth - paddingStart - paddingEnd) / tabCount
        val tabHeight = measuredHeight - paddingTop - paddingBottom

        // 遍历测量子 view 的宽高
        children.forEachIndexed { index, child ->
            val params = child.layoutParams as LayoutParams

            if (child is BottomTab) {
                // 将 tab 添加到数组中
                bottomTabs.add(child)
                // 如果 child 是 tab, 按 tab 的测量方式 measure 宽高
                child.measure(
                        MeasureSpec.makeMeasureSpec(tabWidth - params.leftMargin - params.rightMargin, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(tabHeight - params.topMargin - params.bottomMargin, MeasureSpec.EXACTLY))
            } else {
                // 非 tab 则按照 child 自己的宽高进行测量
                val childWidthMeasureSpec = ViewGroup.getChildMeasureSpec(
                        widthMeasureSpec,
                        paddingStart + paddingEnd + params.leftMargin + params.rightMargin,
                        params.width
                )
                val childHeightMeasureSpec = ViewGroup.getChildMeasureSpec(
                        heightMeasureSpec,
                        paddingTop + paddingBottom + params.topMargin + params.bottomMargin,
                        params.height
                )
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 计算 tab 宽度
        val tabWidth = (measuredWidth - paddingStart - paddingEnd) / tabCount
        // tab 距离左边的距离, 初始值为 bar 的左内边距
        var tabLeft = paddingStart
        var tabIndex = 0
        children.forEachIndexed { _, child ->
            val params = child.layoutParams as LayoutParams

            if (child is BottomTab) {
                // 如果 child 是 tab, 按 tab 的布局方式排列
                // 设置 tab 的选中状态
                child.isChecked = tabIndex == selectedIndex

                // tab 距离顶部的距离为 bar 的内顶边距 和 tab 的外顶边距
                val top = paddingTop + params.topMargin
                tabLeft += params.leftMargin
                if (tabIndex != 0) {
                    // 随着 tab 的遍历, left 以 tabWidth 的大小自增
                    tabLeft += tabWidth
                }
                // 右 & 下 ...
                val right = tabLeft + tabWidth - params.rightMargin
                val bottom = measuredHeight - paddingBottom - params.bottomMargin
                child.layout(tabLeft, top, right, bottom)

                // 左边距还有增加 tab 的右外边距
                tabLeft += params.rightMargin
                // tab 的遍历索引自增
                ++tabIndex
            } else {
                // TODO 支持 layout gravity
                val anchorView = findViewById<View>(params.anchorId)
                if (anchorView != null) {
                    // 若 child 有对应的 anchor, 根据 anchor 的位置进行布局
                    layoutChildWithAnchor(child, params, anchorView)
                } else {
                    val left = paddingStart + params.leftMargin
                    val top = paddingTop + params.topMargin
                    val right = left + child.measuredWidth
                    val bottom = top + child.measuredHeight
                    child.layout(left, top, right, bottom)
                }
            }
        }
    }

    /**
     * 根据 anchor 的位置布局 child
     */
    private fun layoutChildWithAnchor(
            child: View,
            params: LayoutParams,
            anchorView: View
    ) {
        val left: Int
        val right: Int
        when {
            params.anchorGravity and ANCHOR_LEFT == ANCHOR_LEFT &&
                    params.anchorGravity and ANCHOR_RIGHT == ANCHOR_RIGHT -> {
                // 水平居中 (必须优先判断)
                left = anchorView.left + (anchorView.measuredWidth - child.measuredWidth) / 2 + (params.leftMargin - params.rightMargin)
                right = left + child.measuredWidth
            }
            params.anchorGravity and ANCHOR_LEFT == ANCHOR_LEFT -> {
                // 水平居左
                left = anchorView.left + params.leftMargin
                right = left + child.measuredWidth
            }
            params.anchorGravity and ANCHOR_RIGHT == ANCHOR_RIGHT -> {
                // 水平居右
                right = anchorView.right - params.rightMargin
                left = right - child.measuredWidth
            }
            else -> {
                // 默认
                left = (anchorView.measuredWidth - child.measuredWidth) / 2 + (params.leftMargin - params.rightMargin)
                right = left + child.measuredWidth
            }
        }

        val top: Int
        val bottom: Int
        when {
            params.anchorGravity and ANCHOR_TOP == ANCHOR_TOP &&
                    params.anchorGravity and ANCHOR_BOTTOM == ANCHOR_BOTTOM -> {
                // 竖直居中 (必须优先判断)
                top = (anchorView.measuredHeight - child.measuredHeight) / 2 + (params.topMargin - params.bottomMargin)
                bottom = top + child.measuredHeight
            }
            params.anchorGravity and ANCHOR_TOP == ANCHOR_TOP -> {
                // 竖直居顶
                top = anchorView.top + params.topMargin - child.measuredHeight
                bottom = top + child.measuredHeight
            }
            params.anchorGravity and ANCHOR_BOTTOM == ANCHOR_BOTTOM -> {
                // 竖直居底
                bottom = anchorView.bottom - params.bottomMargin + child.measuredHeight
                top = bottom - child.measuredHeight
            }
            else -> {
                // 默认
                top = (anchorView.measuredHeight - child.measuredHeight) / 2 + (params.topMargin - params.bottomMargin)
                bottom = top + child.measuredHeight
            }
        }

        child.layout(left, top, right, bottom)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.MarginLayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.MarginLayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.MarginLayoutParams {
        return LayoutParams(p)
    }

    /**
     * BottomNavigationBar 子 View 的 LayoutParams
     */
    class LayoutParams : ViewGroup.MarginLayoutParams {
        /** 瞄准的 anchor view 的 id, 默认无 */
        var anchorId: Int = View.NO_ID
            private set

        /** 瞄准的方向, 默认居中 */
        var anchorGravity: Int = ANCHOR_CENTER
            private set

        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationBar_Layout)
            anchorId = a.getResourceId(R.styleable.BottomNavigationBar_Layout_x_anchor, View.NO_ID)
            anchorGravity = a.getResourceId(R.styleable.BottomNavigationBar_Layout_x_anchorGravity, ANCHOR_CENTER)
            a.recycle()
        }

        constructor(source: ViewGroup.LayoutParams) : super(source)

        constructor(width: Int, height: Int) : super(width, height)

        companion object {
            // 五个值分别是二进制: 0001, 0010, 0100, 1000, 1111
            const val ANCHOR_LEFT = 1
            const val ANCHOR_TOP = 2
            const val ANCHOR_RIGHT = 4
            const val ANCHOR_BOTTOM = 8
            const val ANCHOR_CENTER = 15
        }
    }
}