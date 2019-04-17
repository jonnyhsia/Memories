package com.arch.jonnyhsia.foundation.component

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    abstract val layoutRes: Int

    abstract val vm: VM

    final override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        commonObserve()
    }

    /**
     * Toast, Router 观察
     */
    private fun commonObserve() {
        vm.toaster.observe(this, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        vm.superNavigator.observe(this, Observer {
            it.go(this)
        })

        vm.status.observe(this, Observer {
            when (it) {
                is PageStatus.Loading -> onPageLoading()
                is PageStatus.IDLE -> onPageIDLE()
                is PageStatus.Failed -> onPageFailed(it)
            }
        })

        vm.closeSignal.observe(this, Observer {
            finish()
        })
    }

    open fun onPageLoading() {
    }

    open fun onPageIDLE() {
    }

    open fun onPageFailed(failure: PageStatus.Failed) {
    }

    protected fun back() {
        activity?.onBackPressed()
    }

    protected fun finish(afterTransition: Boolean = false) {
        if (afterTransition) {
            activity?.finishAfterTransition()
        } else {
            activity?.finish()
        }
    }

    /**
     * 获取 bundle 中对应 key 的值, 类型为默认值
     */
    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    protected inline operator fun <reified T> Bundle?.get(key: String, default: T): T {
        if (this == null) {
            return default
        }

        return when (default) {
            is String -> getString(key, default)
            is Int -> getInt(key).takeIf { it != 0 } ?: getString(key)?.toFloat() ?: default
            is Float -> getFloat(key, getString(key)?.toFloat() ?: default)
            is Boolean -> getBoolean(key, getString(key)?.toBoolean() ?: default)
            is Double -> getDouble(key, getString(key)?.toDouble() ?: default)
            is Byte -> getByte(key, getString(key)?.toByte() ?: default)
            is Long -> getLong(key, getString(key)?.toLong() ?: default)
            is Short -> getShort(key, getString(key)?.toShort() ?: default)
            is Char -> getChar(key, default)
            else -> throw RuntimeException("不支持的类型")
        } as T
    }

    protected fun pageName(): String {
        val contextString = context?.toString() ?: ""
        val start = contextString.lastIndexOf(".") + 1
        val end = contextString.indexOf("@") - 8
        if (start < 0 || end < 0 || start >= end) {
            return ""
        }

        return contextString.substring(start, end)
    }
}