package com.jonnyhsia.appcore.ext

import android.Manifest
import android.content.pm.ActivityInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jonnyhsia.appcore.GlideV4Engine
import com.jonnyhsia.appcore.R
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.appcore.component.xsubscribe
import com.jonnyhsia.appcore.contract.RequestCode
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.SelectionCreator

/**
 * Obtain a default view model
 */
inline fun <reified VM : ViewModel> Fragment.obtainViewModel(): VM {
    return ViewModelProviders.of(this).get(VM::class.java)
}

/**
 * Obtain a view model with custom constructor
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified VM : ViewModel> Fragment.obtainViewModelOf(
        crossinline viewModelCreator: () -> VM
): VM {
    val factory = object : ViewModelProvider.NewInstanceFactory() {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return viewModelCreator() as VM
        }
    }
    return ViewModelProviders.of(this, factory).get(VM::class.java)
}

/**
 * Obtain a default shared view model
 */
inline fun <reified VM : ViewModel> Fragment.obtainSharedViewModel(): VM {
    return ViewModelProviders.of(requireActivity()).get(VM::class.java)
}

/**
 * Obtain a default shared view model with custom constructor
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified VM : ViewModel> Fragment.obtainSharedViewModelOf(
        crossinline viewModelCreator: () -> VM
): VM {
    val factory = object : ViewModelProvider.NewInstanceFactory() {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return viewModelCreator() as VM
        }
    }
    return ViewModelProviders.of(requireActivity(), factory).get(VM::class.java)
}


inline fun BaseFragment<*>.openGallery(crossinline config: SelectionCreator.() -> Unit = {}) {
    rxPermission.request(Manifest.permission.READ_EXTERNAL_STORAGE)
            .xsubscribe(vm, onNext = { granted ->
                if (granted) {
                    Matisse.from(this)
                            .choose(MimeType.ofImage())
                            .theme(R.style.Matisse_Memo)
                            .countable(false)
                            .maxSelectable(1)
                            .spanCount(4)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(0.8f)
                            .imageEngine(GlideV4Engine())
                            .apply(config)
                            .forResult(RequestCode.REQUEST_CHOOSE_IMAGE)
                }
            })
}