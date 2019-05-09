@file:JvmName("DimensionKit")

package com.jonnyhsia.appcore.ext

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.jonnyhsia.appcore.application

val Int.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), application.resources.displayMetrics).toInt()

val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), application.resources.displayMetrics)

val Int.sp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), application.resources.displayMetrics).toInt()

val Float.sp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), application.resources.displayMetrics)

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
        val resourceId = application.resources.getIdentifier("status_bar_height", "dimen", "android").takeIf { it > 0 }
        return resourceId?.let(application.resources::getDimensionPixelSize) ?: 0
    }

val displaySize: Pair<Int, Int>
    get() {
        val wm = ContextCompat.getSystemService(application, WindowManager::class.java)
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
        val wm = ContextCompat.getSystemService(application, WindowManager::class.java)
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
        val wm = ContextCompat.getSystemService(application, WindowManager::class.java)
                ?: return 0
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }