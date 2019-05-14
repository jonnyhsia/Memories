package com.jonnyhsia.memories.page.login

import androidx.lifecycle.MutableLiveData
import com.arch.jonnyhsia.memories.model.passport.PassportDataSource
import com.arch.jonnyhsia.memories.model.passport.bean.LoginType
import com.jonnyhsia.appcore.component.BaseViewModel
import com.jonnyhsia.appcore.ext.isValidEmail
import com.jonnyhsia.appcore.okrx.okSubscribe

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
                .okSubscribe(this, onSuccess = {
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
                        .okSubscribe(this, onSuccess = {
                            navigate(url = "memo://Main")
                            close()
                        }, onError = {
                            toast(it.message)
                        })
            }
            is LoginType.Register -> {
                passportDataSource.register(email, password)
                        .okSubscribe(this, onSuccess = {
                            navigate(url = "memo://Main")
                            close()
                        }, onError = {
                            toast(it.message)
                        })
            }
            else -> {
            }
        }
    }
}