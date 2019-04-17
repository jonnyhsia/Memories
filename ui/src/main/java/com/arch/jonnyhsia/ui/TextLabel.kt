package com.arch.jonnyhsia.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.annotation.CallSuper
import androidx.appcompat.widget.AppCompatTextView

typealias PlaceholderStrategy = (CharSequence?) -> Boolean

open class TextLabel @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), Checkable {

    private val placeholder: String?

    private var _checked: Boolean = false

    private var placeholderStrategy: PlaceholderStrategy? = null

    private val drawableStartScale: Float
    private val drawableTopScale: Float
    private val drawableEndScale: Float
    private val drawableBottomScale: Float

    // @IntDef(value = [STRATEGY_CUSTOM, STRATEGY_TEXT, STRATEGY_NUMERIC])
    private val placeholderType: Int

    private val innerPlaceholderStrategy: PlaceholderStrategy
        get() = placeholderStrategy
                ?: when (placeholderType) {
                    STRATEGY_NUMERIC -> DEFAULT_NUMERIC_PLACEHOLDER_STRATEGY
                    else -> DEFAULT_TEXT_PLACEHOLDER_STRATEGY
                }

    var displayText: CharSequence?
        get() = text
        set(value) {
            text = if (placeholder != null && innerPlaceholderStrategy(value)) {
                placeholder
            } else {
                value
            }
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextLabel)
        placeholder = typedArray.getString(R.styleable.TextLabel_x_placeholder)
        placeholderType =
                typedArray.getInt(R.styleable.TextLabel_x_placeholderStrategy, STRATEGY_CUSTOM)
        // color
        val primaryTextColor =
                typedArray.getColor(R.styleable.TextLabel_x_primaryColor, currentTextColor)
        val highlightTextColor =
                typedArray.getColor(R.styleable.TextLabel_x_highlightColor, primaryTextColor)
        val disabledTextColor =
                typedArray.getColor(R.styleable.TextLabel_x_disabledColor, Color.LTGRAY)
        // drawable
        drawableStartScale = typedArray.getFloat(R.styleable.TextLabel_x_drawableStartScale, 1f)
        drawableTopScale = typedArray.getFloat(R.styleable.TextLabel_x_drawableTopScale, 1f)
        drawableEndScale = typedArray.getFloat(R.styleable.TextLabel_x_drawableEndScale, 1f)
        drawableBottomScale = typedArray.getFloat(R.styleable.TextLabel_x_drawableBottomScale, 1f)
        val drawableStart = typedArray.getDrawable(R.styleable.TextLabel_x_drawableStart)
                ?.also { it.scale(drawableStartScale) }
        val drawableTop = typedArray.getDrawable(R.styleable.TextLabel_x_drawableTop)
                ?.also { it.scale(drawableTopScale) }
        val drawableEnd = typedArray.getDrawable(R.styleable.TextLabel_x_drawableEnd)
                ?.also { it.scale(drawableEndScale) }
        val drawableBottom = typedArray.getDrawable(R.styleable.TextLabel_x_drawableBottom)
                ?.also { it.scale(drawableBottomScale) }
        typedArray.recycle()

        val states = arrayOf(
                // pressed
                intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed),
                // focused
                intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused),
                // checked
                intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked),
                // disabled
                intArrayOf(-android.R.attr.state_enabled),
                // normal
                intArrayOf()
        )
        val colors = intArrayOf(
                highlightTextColor,
                highlightTextColor,
                highlightTextColor,
                disabledTextColor,
                primaryTextColor
        )

        setTextColor(ColorStateList(states, colors))
        setCompoundDrawablesRelative(drawableStart, drawableTop, drawableEnd, drawableBottom)
    }

    final override fun setTextColor(colors: ColorStateList?) {
        super.setTextColor(colors)
    }

    final override fun setCompoundDrawablesRelative(
            start: Drawable?,
            top: Drawable?,
            end: Drawable?,
            bottom: Drawable?
    ) {
        super.setCompoundDrawablesRelative(start, top, end, bottom)
    }

    fun setPlaceholderStrategy(placeholderStrategy: PlaceholderStrategy) {
        this.placeholderStrategy = placeholderStrategy
    }

    override fun setCompoundDrawablesRelativeWithIntrinsicBounds(
            start: Drawable?,
            top: Drawable?,
            end: Drawable?,
            bottom: Drawable?
    ) {
        start?.scale(drawableStartScale)
        top?.scale(drawableTopScale)
        end?.scale(drawableEndScale)
        bottom?.scale(drawableBottomScale)
        setCompoundDrawablesRelative(start, top, end, bottom)
    }

    override fun isChecked() = _checked

    override fun toggle() {
        isChecked = !_checked
    }

    @CallSuper
    override fun setChecked(checked: Boolean) {
        _checked = checked
        refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (_checked) {
            View.mergeDrawableStates(drawableState, STATE_CHECKED)
        }
        return drawableState
    }

    companion object {
        private val STATE_CHECKED = intArrayOf(android.R.attr.state_checked)

        private const val STRATEGY_CUSTOM = 0
        private const val STRATEGY_TEXT = 1
        private const val STRATEGY_NUMERIC = 2

        private val DEFAULT_TEXT_PLACEHOLDER_STRATEGY: PlaceholderStrategy = {
            it.isNullOrEmpty()
        }
        private val DEFAULT_NUMERIC_PLACEHOLDER_STRATEGY: PlaceholderStrategy = {
            it.isNullOrEmpty() || it == "0"
        }
    }

    private fun Drawable.scale(scale: Float) {
        setBounds(0, 0, (intrinsicWidth * scale).toInt(), (intrinsicHeight * scale).toInt())
    }
}