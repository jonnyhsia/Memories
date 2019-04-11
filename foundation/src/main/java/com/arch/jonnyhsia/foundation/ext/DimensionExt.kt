@file:JvmName("DimensionKit")

package com.arch.jonnyhsia.foundation.ext

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import com.arch.jonnyhsia.foundation.Foundation

val Int.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Foundation.innerContext.resources.displayMetrics).toInt()

val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Foundation.innerContext.resources.displayMetrics)

val Int.sp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Foundation.innerContext.resources.displayMetrics).toInt()

val Float.sp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Foundation.innerContext.resources.displayMetrics)

/**
 * px 转 sp
 */
fun Context.px2sp(pxValue: Float): Float =
        pxValue / resources.displayMetrics.scaledDensity

fun View.px2sp(pxValue: Float) = context.px2sp(pxValue)

/**
 * 获取状态栏高度
 */
val statusBarHeight: Int
    get() {
        val resourceId = Foundation.innerContext.resources.getIdentifier("status_bar_height", "dimen", "android").takeIf { it > 0 }
        return resourceId?.let(Foundation.innerContext.resources::getDimensionPixelSize) ?: 0
    }

val displaySize: Pair<Int, Int>
    get() {
        val wm = Foundation.innerContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                ?: return 0 to 0
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        with(outMetrics) {
            return widthPixels to heightPixels
        }
    }

/**
 * 获取屏幕宽度
 */
val displayHeight: Int
    get() {
        val wm = Foundation.innerContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                ?: return 0
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

/**
 * 获取屏幕宽度
 */
val displayWidth: Int
    get() {
        val wm = Foundation.innerContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                ?: return 0
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }