package com.arch.jonnyhsia.foundation.ext

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import com.arch.jonnyhsia.foundation.Foundation
import com.arch.jonnyhsia.foundation.helper.RoundCorner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition

object Colors {

    @ColorInt
    operator fun invoke(res: Int): Int {
        return ContextCompat.getColor(Foundation.innerContext, res)
    }
}

fun String.measure(paint: Paint): Rect {
    val rect = Rect()
    paint.getTextBounds(this, 0, length, rect)
    return rect
}

fun ImageView.load(source: Any?) {
    if (source == null) {
        return
    }
    Glide.with(this)
            .load(source)
            .into(this)
}

var TextView.displayText: CharSequence?
    get() = this.text
    set(value) {
        if (value.isNullOrEmpty()) {
            if (visibility != View.GONE) {
                visibility = View.GONE
            }
        } else {
            text = value
            if (visibility != View.VISIBLE) {
                visibility = View.VISIBLE
            }
        }
    }

fun AppCompatTextView.setTextFuture(charSequence: CharSequence, autoHide: Boolean = true) {
    if (charSequence.isEmpty() && visibility == View.VISIBLE) {
        visibility = View.GONE
        return
    }

    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }

    // 如果字数少于 20, 直接使用 setText()
    if (charSequence.length < 20) {
        text = charSequence
        return
    }

    setTextFuture(PrecomputedTextCompat.getTextFuture(
            charSequence,
            TextViewCompat.getTextMetricsParams(this),
            null
    ))
}

fun ImageView.load(source: Any?, option: RequestOptions.() -> Unit) {
    if (source == null) {
        return
    }
    val requestOptions = RequestOptions()
    option(requestOptions)

    Glide.with(this)
            .load(source)
            .apply(requestOptions)
            .into(this)
}

fun TextView.loadDrawable(source: Any?, direction: Int = Gravity.START, option: RequestOptions.() -> Unit) {
    if (source == null) {
        return
    }
    val requestOptions = RequestOptions()
    option(requestOptions)

    Glide.with(this)
            .load(source)
            .apply(requestOptions)
            .into(object : CustomViewTarget<TextView, Drawable>(this) {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    when (direction) {
                        Gravity.START -> setCompoundDrawablesRelativeWithIntrinsicBounds(resource, null, null, null)
                        Gravity.TOP -> setCompoundDrawablesRelativeWithIntrinsicBounds(null, resource, null, null)
                        Gravity.END -> setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, resource, null)
                        Gravity.BOTTOM -> setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resource)
                    }
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                }
            })
}

@SuppressLint("CheckResult")
fun RequestOptions.asAvatar() {
    circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
}

@SuppressLint("CheckResult")
fun RequestOptions.asRounded(radius: Int) {
    transforms(CenterCrop(), RoundedCorners(radius))
//            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
}

@SuppressLint("CheckResult")
fun RequestOptions.asRounded(
        leftTop: Float = 0f,
        rightTop: Float = 0f,
        rightBottom: Float = 0f,
        leftBottom: Float = 0f
) {
    transforms(CenterCrop(), RoundCorner(leftTop, rightTop, rightBottom, leftBottom))
//            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
}