@file:JvmName("TextKit")

package com.jonnyhsia.appcore.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.*
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.text.parseAsHtml
import com.jonnyhsia.appcore.application


inline fun CharSequence.spannable(block: Spannable.() -> Unit = {}): Spannable {
    return if (this is Spannable) {
        this.apply(block)
    } else {
        SpannableString(this).apply(block)
    }
}

fun CharSequence.spannableBuilder(
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): SpannableStringBuilder {
    return if (this is SpannableStringBuilder) {
        this
    } else {
        SpannableStringBuilder(this, start, end)
    }
}

/**
 * 文字大小
 */
fun Spannable.setSize(
        textSize: Int,
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): Spannable {
    setSpan(AbsoluteSizeSpan(textSize, true), start, end, flag)
    return this
}

/**
 * 文字样式
 * @param style [StyleSpan] 的 style
 */
fun Spannable.setTextStyle(
        style: Int,
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): Spannable {
    setSpan(StyleSpan(style), start, end, flag)
    return this
}

/**
 * 文字颜色
 */
fun Spannable.setForeground(
        @ColorInt color: Int,
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): Spannable {
    setSpan(ForegroundColorSpan(color), start, end, flag)
    return this
}

/**
 * 文字中横线(删除线)
 */
fun Spannable.setStrikethrough(
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): Spannable {
    setSpan(StrikethroughSpan(), start, end, flag)
    return this
}

/**
 * 文字下划线
 */
fun Spannable.setUnderline(
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): Spannable {
    setSpan(UnderlineSpan(), start, end, flag)
    return this
}

/**
 * 文字设置上标
 */
fun Spannable.setSuperscript(
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): Spannable {
    setSpan(SuperscriptSpan(), start, end, flag)
    return this
}

/**
 * 文字设置下标
 */
fun Spannable.setSubscript(
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): Spannable {
    setSpan(SubscriptSpan(), start, end, flag)
    return this
}

/**
 * 文字替换为图片
 */
fun Spannable.setImage(
        drawable: Drawable,
        verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM,
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): Spannable {
    setSpan(ImageSpan(drawable, verticalAlignment), start, end, flag)
    return this
}

/**
 * 文字替换为图片
 */
fun Spannable.setImage(
        bitmap: Bitmap,
        verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM,
        start: Int = 0,
        end: Int = this.length,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
): Spannable {
    setSpan(ImageSpan(application, bitmap, verticalAlignment), start, end, flag)
    return this
}

private fun Int.startInclusive(): Boolean {
    return this == SpannedString.SPAN_INCLUSIVE_INCLUSIVE
            || this == SpannedString.SPAN_INCLUSIVE_EXCLUSIVE
}

private fun Int.endInclusive(): Boolean {
    return this == SpannedString.SPAN_INCLUSIVE_INCLUSIVE
            || this == SpannedString.SPAN_EXCLUSIVE_INCLUSIVE
}

/**
 * 文字点击
 */
inline fun Spannable.setClickable(
        @ColorInt textColor: Int,
        start: Int = 0,
        end: Int = this.length,
        drawUnderline: Boolean = false,
        flag: Int = SpannedString.SPAN_INCLUSIVE_EXCLUSIVE,
        crossinline clickable: (View) -> Unit
): Spannable {
    setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            clickable(widget)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = textColor
            ds.isUnderlineText = drawUnderline
        }
    }, start, end, flag)
    return this
}

/**
 * 重写 Spannable 的加法运算符
 */
operator fun Spannable.plus(another: CharSequence): SpannableStringBuilder {
    return when {
        this is SpannableStringBuilder -> {
            this.append(another)
            this
        }
        another is SpannableStringBuilder -> {
            another.append(this, 0, this.length)
            another
        }
        else -> {
            SpannableStringBuilder(this) + another
        }
    }
}

fun String.parse(): Spanned {
    return parseAsHtml()
}