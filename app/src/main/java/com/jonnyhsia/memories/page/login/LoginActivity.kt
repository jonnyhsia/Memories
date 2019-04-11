package com.jonnyhsia.memories.page.login

import android.os.Bundle
import com.arch.jonnyhsia.compass.api.Route
import com.arch.jonnyhsia.foundation.component.BaseActivity
import com.jonnyhsia.memories.R

@Route(scheme = "*", name = "Login", requestCode = 10)
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragmentById(R.id.container) {
            LoginFragment()
        }
    }
}
