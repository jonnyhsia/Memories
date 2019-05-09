package com.jonnyhsia.memories.page.welcome

import android.os.Bundle
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.compass.api.Route
import com.arch.jonnyhsia.memories.model.passport.bean.UserModel
import com.jonnyhsia.appcore.component.BaseActivity
import com.jonnyhsia.appcore.livebus.LiveBus
import com.jonnyhsia.memories.R

@Route(name = "Welcome")
class WelcomeActivity : BaseActivity() {

    private val onUserLoginChanged = Observer<UserModel> {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragmentById(R.id.container) {
            WelcomeFragment()
        }

        LiveBus.observeForever(onUserLoginChanged)
    }

    override fun onDestroy() {
        super.onDestroy()
        LiveBus.unsubscribe(onUserLoginChanged)
    }
}