@file:JvmName("TextKit")

package com.jonnyhsia.appcore.ext

import android.graphics.drawable.Drawable
import android.text.*
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.text.HtmlCompat
import androidx.core.text.parseAsHtml
import io.reactivex.Observable
import java.util.concurrent.Future


inline fun CharSequence.spannable(block: Spannable.() -> Unit = {}): Spannable {
    return if (this is Spannable) {
        this.apply(block)
    } else {
        SpannableString(this).apply(block)
    }
}

fun CharSequence.spannableBuilder(
        start: Int = 0,
        end: Int = this.length
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
        end: Int = this.length
): Spannable {
    setSpan(AbsoluteSizeSpan(textSize, true), start, end, SpannedString.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 文字样式
 * @param style [StyleSpan] 的 style
 */
fun Spannable.setTextStyle(
        style: Int,
        start: Int = 0,
        end: Int = this.length
): Spannable {
    setSpan(StyleSpan(style), start, end, SpannedString.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 文字颜色
 */
fun Spannable.setForeground(
        @ColorInt color: Int,
        start: Int = 0,
        end: Int = this.length
): Spannable {
    setSpan(ForegroundColorSpan(color), start, end, SpannedString.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 文字中横线(删除线)
 */
fun Spannable.setStrikethrough(
        start: Int = 0,
        end: Int = this.length
): Spannable {
    setSpan(StrikethroughSpan(), start, end, SpannedString.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 文字下划线
 */
fun Spannable.setUnderline(
        start: Int = 0,
        end: Int = this.length
): Spannable {
    setSpan(UnderlineSpan(), start, end, SpannedString.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 文字设置上标
 */
fun Spannable.setSuperscript(
        start: Int = 0,
        end: Int = this.length
): Spannable {
    setSpan(SuperscriptSpan(), start, end, SpannedString.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 文字设置下标
 */
fun Spannable.setSubscript(
        start: Int = 0,
        end: Int = this.length
): Spannable {
    setSpan(SubscriptSpan(), start, end, SpannedString.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 文字替换为图片
 */
fun Spannable.setImage(
        drawable: Drawable,
        verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM,
        start: Int = 0,
        end: Int = this.length
): Spannable {
    setSpan(ImageSpan(drawable, verticalAlignment), start, end, SpannedString.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 文字点击
 */
inline fun Spannable.setClickable(
        @ColorInt textColor: Int,
        start: Int = 0,
        end: Int = this.length,
        drawUnderline: Boolean = false,
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
    }, start, end, SpannedString.SPAN_INCLUSIVE_EXCLUSIVE)
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