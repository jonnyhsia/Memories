package com.jonnyhsia.memories.page.login

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.memories.model.passport.bean.LoginType
import com.jakewharton.rxbinding2.widget.textChanges
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.component.xsubscribe
import com.jonnyhsia.appcore.ext.Colors
import com.jonnyhsia.appcore.ext.dp
import com.jonnyhsia.memories.R
import com.jonnyhsia.memories.kit.KeyboardWatcher
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class LoginFragment : BaseFragment<LoginViewModel>() {

    override val vm: LoginViewModel by viewModel {
        parametersOf(arguments["entry", ""])
    }

    override val layoutRes: Int
        get() = R.layout.login_fragment

    private var keyboardWatcher: KeyboardWatcher by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textLoginTitle.setFactory {
            TextView(context).apply {
                textSize = 24f
                letterSpacing = 0.05f
                setTextColor(Colors(R.color.textColorPrimary))
                gravity = Gravity.CENTER
                typeface = Typeface.DEFAULT_BOLD
            }
        }
        textLoginDescription.setFactory {
            TextView(context).apply {
                textSize = 14f
                letterSpacing = 0.05f
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                gravity = Gravity.CENTER
                includeFontPadding = false
                setLineSpacing(2f.dp, 1f)
                setTextColor(Colors(R.color.textColorSecondary))
            }
        }
        textLoginTitle.setText("输入你的电子邮箱")
        textLoginDescription.setText(getString(R.string.login_description))

        keyboardWatcher = KeyboardWatcher(requireActivity())
        keyboardWatcher.setListener(object : KeyboardWatcher.SimpleKeyboardToggleListener() {
            override fun onKeyboardVisibilityChanged(isVisible: Boolean) {
                imgLoginHeader.isVisible = !isVisible
            }
        })

        fieldEmail.textChanges()
                .throttleLatest(600L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .xsubscribe(vm, onNext = {
                    vm.onEmailChanges(it.toString())
                })

        btnLogin.setOnClickListener {
            vm.onClickLogin(fieldEmail.text.toString(), fieldPassword.text.toString())
        }

        vm.loginType.observe(this, Observer {
            when (it) {
                is LoginType.Illegal -> {
                    fieldPassword.isVisible = false
                    btnLogin.text = "······"
                    textLoginTitle.setText("输入你的电子邮箱")
                    textLoginDescription.setText(getString(R.string.login_description))
                }
                is LoginType.LoginDirectly -> {
                    fieldPassword.isVisible = false
                    btnLogin.text = "${it.username}，来快活呀"
                    textLoginDescription.setText(getString(R.string.login_directly_description))
                    textLoginTitle.setText("直接登录账号")
                }
                is LoginType.LoginWithPassword -> {
                    fieldPassword.isVisible = true
                    btnLogin.text = "时候这么晚了，快进来吧"
                    textLoginDescription.setText(getString(R.string.login_password_description))
                    textLoginTitle.setText("输入密码后完成登录")
                }
                is LoginType.Register -> {
                    fieldPassword.isVisible = true
                    btnLogin.text = "时候这么晚了，快进来吧"
                    textLoginTitle.setText("注册完成还需一步")
                    textLoginDescription.setText(getString(R.string.register_description))
                }
            }
        })
    }
}