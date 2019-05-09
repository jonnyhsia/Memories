package com.jonnyhsia.memories.page.main

import androidx.lifecycle.ViewModel
import com.arch.jonnyhsia.compass.Compass
import com.arch.jonnyhsia.compass.RouteIntent
import com.arch.jonnyhsia.memories.model.msic.MiscDataSource

class MainViewModel(
        private val miscDataSource: MiscDataSource
) : ViewModel() {

    /**
     * 当进入首页时
     */
    fun onEnterHome(): RouteIntent? {
        val enteredWelcomePage = miscDataSource.haveWelcomePageEntered()
        if (!enteredWelcomePage) {
            return Compass.navigate("memo://Welcome")
        }

        return null
    }
}