package com.jonnyhsia.appcore.component

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.jonnyhsia.appcore.R

abstract class BaseActivity : AppCompatActivity() {

    open val layoutRes: Int
        get() = R.layout.activity_blank_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    /**
     * 根据 container id 添加并显示 Fragment
     */
    protected inline fun showFragmentById(id: Int, factory: () -> Fragment) {
        val fragment = supportFragmentManager.findFragmentById(id)
            ?: factory()

        val args = intent?.extras
        if (args != null) {
            val existedArgs = fragment.arguments
            if (existedArgs == null) {
                fragment.arguments = Bundle(intent.extras)
            } else {
                existedArgs.putAll(intent.extras)
            }
        }

        supportFragmentManager.transaction {
            if (fragment.isAdded && fragment.isHidden) {
                show(fragment)
            } else {
                add(id, fragment)
            }
        }
    }

    /**
     * 设置状态栏颜色
     */
    fun setStatusBarColor(color: Int, isLight: Boolean) {
        // TODO:
    }

    /**
     * 透明化状态栏
     */
    fun transluentStatusBar(enable: Boolean) {
        // TODO:
    }

    /**
     * 设置窗口背景颜色
     */
    fun setBackgroundColor(@ColorInt color: Int) {
        window.setBackgroundDrawable(ColorDrawable(color))
    }
}