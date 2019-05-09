package com.jonnyhsia.appcore.livebus

import android.os.Looper
import androidx.collection.ArrayMap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.mirror.logger.Corgi
import com.arch.jonnyhsia.mirror.logger.logw

object LiveBus : Corgi {

    private val bus = ArrayMap<String, MutableLiveData<Any>>()

    /**
     * 发送事件
     */
    fun dispatch(value: Any) {
        val branch = value::class.java.canonicalName
                ?: throw IllegalArgumentException("不支持匿名对象")
        val liveData = bus[branch]
        if (liveData == null) {
            logw("你还没有注册相应的 LiveData")
            return
        }

        if (isMainThread()) {
            liveData.value = value
        } else {
            liveData.postValue(value)
        }
    }

    inline fun <reified T : Any> observe(owner: LifecycleOwner, observer: Observer<T>) {
        val branch = T::class.java.canonicalName
                ?: throw IllegalArgumentException("不支持匿名对象")
        val liveData = branchOf<T>(branch)
        if (liveData == null) {
            logw("你还没有注册相应的 LiveData")
            return
        }

        liveData.observe(owner, observer)
    }

    /**
     * 无视 Lifecycle 进行观察，需要手动注销监听
     */
    inline fun <reified T : Any> observeForever(observer: Observer<T>) {
        val branch = T::class.java.canonicalName
                ?: throw IllegalArgumentException("不支持匿名对象")
        val liveData = branchOf<T>(branch)
        if (liveData == null) {
            logw("你还没有注册相应的 LiveData")
            return
        }

        liveData.observeForever(observer)
    }

    /**
     * 无视 Lifecycle 进行观察，需要手动注销监听
     */
    inline fun <reified T : Any> removeObserver(observer: Observer<T>) {
        val branch = T::class.java.canonicalName
                ?: throw IllegalArgumentException("不支持匿名对象")
        val liveData = branchOf<T>(branch)
        if (liveData == null) {
            logw("你还没有注册相应的 LiveData")
            return
        }

        liveData.removeObserver(observer)
    }

    /**
     * 为 inline function 提供私有 API，别用
     */
    fun <T : Any> branchOf(clz: String): MutableLiveData<T>? {
        return bus[clz] as? MutableLiveData<T>
    }

    private fun isMainThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }

}