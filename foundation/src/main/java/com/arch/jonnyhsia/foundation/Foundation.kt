package com.arch.jonnyhsia.foundation

import android.content.Context
import kotlin.properties.Delegates

object Foundation {

    internal var innerContext: Context by Delegates.notNull()
        private set

    fun attachApplication(context: Context) {
        innerContext = context.applicationContext
    }
}