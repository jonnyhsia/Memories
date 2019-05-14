package com.jonnyhsia.appcore.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arch.jonnyhsia.compass.Compass
import com.arch.jonnyhsia.compass.RouteIntent

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