package com.jonnyhsia.appcore.ui

import android.graphics.drawable.Drawable

inline fun rectangle(solidColor: Int, block: Drawables.() -> Unit): Drawable {
    return Drawables.ofRectangle(solidColor).apply(block).build()
}

inline fun rectangle(vararg colors: Int, block: Drawables.() -> Unit): Drawable {
    return Drawables.ofRectangle(*colors).apply(block).build()
}

inline fun oval(solidColor: Int, block: Drawables.() -> Unit): Drawable {
    return Drawables.ofOval(solidColor).apply(block).build()
}

inline fun oval(vararg colors: Int, block: Drawables.() -> Unit): Drawable {
    return Drawables.ofOval(*colors).apply(block).build()
}

inline fun line(solidColor: Int, block: Drawables.() -> Unit): Drawable {
    return Drawables.ofLine(solidColor).apply(block).build()
}

inline fun line(vararg colors: Int, block: Drawables.() -> Unit): Drawable {
    return Drawables.ofLine(*colors).apply(block).build()
}

inline fun ring(solidColor: Int, block: Drawables.() -> Unit): Drawable {
    return Drawables.ofRing(solidColor).apply(block).build()
}

inline fun ring(vararg colors: Int, block: Drawables.() -> Unit): Drawable {
    return Drawables.ofRing(*colors).apply(block).build()
}