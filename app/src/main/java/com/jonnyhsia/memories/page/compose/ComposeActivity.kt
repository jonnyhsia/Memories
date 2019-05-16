package com.jonnyhsia.memories.page.compose

import android.os.Bundle
import com.arch.jonnyhsia.compass.api.Route
import com.jonnyhsia.appcore.component.BaseActivity
import com.jonnyhsia.memories.R

@Route(name = "Compose")
class ComposeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            showFragmentById(R.id.container) {
                ComposeFragment()
            }
        }
    }
}
