package com.jonnyhsia.memories.router

import com.arch.jonnyhsia.compass.ProcessableIntent
import com.arch.jonnyhsia.compass.interceptor.SchemeInterceptor

object WebInterceptor : SchemeInterceptor {

    override fun intercept(intent: ProcessableIntent) {
        val scheme = intent.uri.scheme
        if ("http" == scheme || "https" == scheme) {
            // 是内嵌页
            intent.redirect("*://Web").addParameter("url", intent.uri)
        }
    }
}