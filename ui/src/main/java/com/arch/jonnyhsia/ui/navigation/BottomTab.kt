package com.arch.jonnyhsia.ui.navigation

import android.content.Context
import android.util.AttributeSet
import com.arch.jonnyhsia.ui.TextLabel

class BottomTab @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextLabel(context, attrs, defStyleAttr) {

    init {
        isClickable = true
        isFocusable = true
    }

    override fun performClick(): Boolean {
        val handled = super.performClick()
        if (!handled) {
            // 播放点击的默认音效
            playSoundEffect(0)
        }
        // 设置为选中状态
        if (!isChecked) {
            isChecked = true
        }
        // 若 parent 是 Bottom Navigation Bar
        // 调用其 selectedTab 方法触发其选中/复选回调
        (parent as? BottomNavigationBar)?.selectedTab(this)
        return true
    }

//    data class BottomTabModel(
//            val text: String,
//            val icon: Any
//    )
}