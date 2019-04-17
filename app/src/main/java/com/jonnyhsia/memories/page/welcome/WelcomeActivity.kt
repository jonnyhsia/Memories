package com.jonnyhsia.memories.page.welcome

import android.os.Bundle
import com.arch.jonnyhsia.compass.api.Route
import com.arch.jonnyhsia.foundation.component.BaseActivity
import com.arch.jonnyhsia.foundation.event.LoginEvent
import com.jonnyhsia.memories.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@Route(name = "Welcome")
class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        showFragmentById(R.id.container) {
            WelcomeFragment()
        }
    }

    @Subscribe
    fun onLogin(event: LoginEvent) {
        finish()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}