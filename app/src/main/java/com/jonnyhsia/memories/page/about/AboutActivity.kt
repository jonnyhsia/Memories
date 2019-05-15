package com.jonnyhsia.memories.page.about

import android.os.Bundle
import com.arch.jonnyhsia.compass.api.Route
import com.jonnyhsia.appcore.component.BaseActivity
import com.jonnyhsia.memories.R

@Route(name = "About")
class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragmentById(R.id.container) {
            AboutFragment()
        }
    }
}