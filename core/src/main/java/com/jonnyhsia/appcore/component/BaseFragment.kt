package com.jonnyhsia.appcore.component

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.mirror.logger.Corgi
import com.jonnyhsia.appcore.okrx.OkNotification

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), Corgi {

    private var progressDialog: Dialog? = null

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
            toast(it)
        })

        vm.superNavigator.observe(this, Observer {
            it.go(this)
        })

        vm.closeSignal.observe(this, Observer {
            finish()
        })
    }

    protected fun toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), text, duration).show()
    }

    fun enableLoadingView(observer: Observer<OkNotification?>? = null) {
        vm.loadingView.observe(this, observer ?: Observer {
            if (it == null) {
                if (progressDialog?.isShowing == true) {
                    progressDialog?.dismiss()
                }
            } else {
                if (progressDialog == null) {
                    progressDialog = AlertDialog.Builder(requireContext())
                            .setTitle("Loading...")
                            .setMessage("Be patient, the request will be finished.")
                            .create()
                }
                progressDialog?.show()
            }
        })
    }

    fun enableEmptyView(emptyView: View) {
        vm.emptyView.observe(this, Observer {
            emptyView.isVisible = it != null
        })
    }

    fun enableEmptyView(observer: Observer<OkNotification?>) {
        vm.emptyView.observe(this, observer)
    }

    fun enableRefreshLayout(observer: Observer<OkNotification?>) {
        vm.refreshLayout.observe(this, observer)
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
        val end = contextString.indexOf("@") - "Fragment".length
        if (start < 0 || end < 0 || start >= end) {
            return ""
        }

        return contextString.substring(start, end)
    }
}