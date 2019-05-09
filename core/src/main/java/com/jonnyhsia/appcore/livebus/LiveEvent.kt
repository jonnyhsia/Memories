package com.jonnyhsia.appcore.livebus

import android.os.Handler
import android.os.Looper
import androidx.collection.ArrayMap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


class LiveEvent<T : Any>(val key: String) : LiveObservable<T> {

    private val observerMap = ArrayMap<Observer<T>, ObserverWrapper<T>>()

    companion object {
        private val mainHandler = Handler(Looper.getMainLooper())
    }

    private val liveData = MutableLiveData<T>()

    override fun dispatch(value: T, delay: Long) {
        if (delay != 0L) {
            mainHandler.postDelayed({
                liveData.value = value
            }, delay)
            return
        }

        if (isMainThread()) {
            liveData.value = value
        } else {
            liveData.postValue(value)
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        val observerWrapper = ObserverWrapper(observer)
        observerMap[observer] = observerWrapper
        liveData.observe(owner, observer)
    }

    override fun observeForever(observer: Observer<T>) {
        observerMap[observer] = ObserverWrapper(observer)
        liveData.observeForever(observer)
    }

    override fun unsubscribe(observer: Observer<T>) {
        val realObserver = observerMap[observer] ?: observer
        LiveBus.unregister(key)
        liveData.removeObserver(realObserver)
    }

    private fun isMainThread(): Boolean {
        return true
    }

    class ObserverWrapper<T>(
            private val observer: Observer<T>
    ) : Observer<T> {

        override fun onChanged(t: T) {
            observer.onChanged(t)
        }
    }
}