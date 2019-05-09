package com.jonnyhsia.appcore.livebus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * 与 RxJava 的 Observable 没有关系
 */
interface LiveObservable<T: Any> {

    fun dispatch(value: T, delay: Long)

    fun observe(owner: LifecycleOwner, observer: Observer<T>)

    fun observeForever(observer: Observer<T>)

    fun unsubscribe(observer: Observer<T>)
}