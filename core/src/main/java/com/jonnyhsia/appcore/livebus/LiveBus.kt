package com.jonnyhsia.appcore.livebus

import androidx.collection.ArrayMap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

object LiveBus {

    // 使用 LiveBus 前必须先指定 Container Type
    private val bus: MutableMap<String, LiveEvent<Any>>

    init {
        bus = when (Config.container) {
            is BusContainerType.ArrayMap, null -> ArrayMap()
            is BusContainerType.HashMap -> HashMap()
        }
    }

    inline fun <reified T : Any> observe(owner: LifecycleOwner, observer: Observer<T>) {
        with<T>(T::class.java.canonicalName!!).observe(owner, observer)
    }

    inline fun <reified T : Any> observeForever(observer: Observer<T>) {
        with<T>(T::class.java.canonicalName!!).observeForever(observer)
    }

    inline fun <reified T : Any> dispatch(value: T, delay: Long = 0) {
        with<T>(T::class.java.canonicalName!!).dispatch(value, delay)
    }

    inline fun <reified T : Any> unsubscribe(observer: Observer<T>) {
        with<T>(T::class.java.canonicalName!!).unsubscribe(observer)
    }

    fun <T : Any> with(key: String): LiveObservable<T> {
        if (!isKeyRegistered(key)) {
            return registerKey(key)
        }
        return observableOf(key)
    }

    private fun <T : Any> registerKey(key: String): LiveObservable<T> {
        val liveEvent = LiveEvent<T>(key)
        bus.put(key, liveEvent as LiveEvent<Any>)
        return liveEvent
    }

    private fun <T : Any> observableOf(key: String): LiveObservable<T> {
        val liveEvent = bus[key]
        return liveEvent as LiveObservable<T>
    }

    private fun isKeyRegistered(key: String): Boolean {
        return bus.containsKey(key)
    }

    fun unregister(key: String) {
        bus.remove(key)
    }

    object Config {
        var container: BusContainerType? = null

        operator fun invoke(config: Config.() -> Unit) {
            config(this)
        }
    }
}

val Any.liveBusKey: String
    get() = this::class.java.canonicalName!!