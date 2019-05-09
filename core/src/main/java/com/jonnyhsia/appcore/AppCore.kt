package com.jonnyhsia.appcore

import android.content.Context
import kotlin.properties.Delegates

internal var application: Context by Delegates.notNull()
    private set

object AppCore {

    fun attachApplication(context: Context) {
        application = context.applicationContext
    }
}