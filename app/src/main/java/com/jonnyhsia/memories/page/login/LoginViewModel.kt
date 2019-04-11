package com.jonnyhsia.memories.page.login

import androidx.lifecycle.MutableLiveData
import com.arch.jonnyhsia.foundation.component.BaseViewModel
import com.arch.jonnyhsia.foundation.component.xsubscribe
import com.arch.jonnyhsia.foundation.ext.isValidEmail
import com.arch.jonnyhsia.memories.model.passport.PassportDataSource
import com.arch.jonnyhsia.memories.model.passport.bean.LoginType

class LoginViewModel(
        private val entry: String,
        private val passportDataSource: PassportDataSource
) : BaseViewModel() {

    init {
        passportDataSource.logout()
    }

    /**
     * 直接登录:1; 密码登录:2; 密码注册:3.
     */
    val loginType = MutableLiveData<LoginType>()

    fun onEmailChanges(email: String) {
        if (!email.isValidEmail()) {
            if (loginType.value != null && loginType.value !is LoginType.Illegal) {
                loginType.value = LoginType.Illegal()
            }
            return
        }

        passportDataSource.checkUserLoginEntry(email)
                .xsubscribe(this, onSuccess = {
                    if (loginType.value != it) {
                        loginType.value = it
                    }
                })
    }

    fun onClickLogin(email: String, password: String) {
        when (loginType.value) {
            is LoginType.LoginDirectly,
            is LoginType.LoginWithPassword -> {
                passportDataSource.loginWithCode(email, password)
                        .xsubscribe(this, onSuccess = {
                            navigate(url = "memo://Main", close = true)
                        }, onError = {
                            toast(it.message)
                        })
            }
            is LoginType.Register -> {
                passportDataSource.register(email, password)
                        .xsubscribe(this, onSuccess = {
                            navigate(url = "memo://Main", close = true)
                        }, onError = {
                            toast(it.message)
                        })
            }
            else -> {
            }
        }
    }
}