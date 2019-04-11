package com.jonnyhsia.memories.page.welcome

import android.os.Bundle
import com.arch.jonnyhsia.compass.api.Route
import com.arch.jonnyhsia.foundation.component.BaseActivity
import com.jonnyhsia.memories.R

@Route(name = "Welcome")
class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragmentById(R.id.container) {
            WelcomeFragment()
        }
    }
}