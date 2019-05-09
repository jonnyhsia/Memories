package com.jonnyhsia.appcore.ext

import android.app.ActivityManager
import android.content.Context
import android.os.Process
import androidx.core.content.ContextCompat

val Context.currentProcessName: String
    get() {
        val pid = Process.myPid()
        val manager = ContextCompat.getSystemService(this, ActivityManager::class.java)
        return manager?.runningAppProcesses?.firstOrNull { it.pid == pid }?.processName ?: ""
    }

fun Context.isMainProcess(): Boolean {
    return applicationContext.packageName == currentProcessName
}