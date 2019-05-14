package com.jonnyhsia.appcore.okrx

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

abstract class OkObserver<T> : SingleObserver<T> {

    private var emptyView: MutableLiveData<OkNotification?>? = null
    private var refreshLayout: MutableLiveData<OkNotification?>? = null
    private var loadingView: MutableLiveData<OkNotification?>? = null

    @CallSuper
    override fun onSubscribe(d: Disposable) {
        // 显示刷新与进度条
        refreshLayout.callIfRealChanged(OkNotification)
        loadingView.callIfRealChanged(OkNotification)
    }

    @CallSuper
    override fun onSuccess(t: T) {
        // 隐藏空布局, 刷新与进度条
        emptyView.callIfRealChanged()
        refreshLayout.callIfRealChanged()
        loadingView.callIfRealChanged()
    }

    @CallSuper
    override fun onError(e: Throwable) {
        // 显示空布局, 隐藏刷新与进度条
        emptyView.callIfRealChanged(OkNotification)
        refreshLayout.callIfRealChanged()
        loadingView.callIfRealChanged()
    }

    fun attachWithPacket(okPacket: OkTempPacket) {
        emptyView = okPacket.emptyView
        refreshLayout = okPacket.refreshLayout
        loadingView = okPacket.loadingView
    }

    private fun <T> MutableLiveData<T>?.callIfRealChanged(newValue: T? = null) {
        if (this != null && value != newValue) {
            this.value = newValue
        }
    }
}


/**
 * OkObserver 的形式订阅
 */
inline fun <T> Single<T>.okSubscribe(
    dc: DisposableContainer,
    crossinline onSuccess: (T) -> Unit,
    crossinline onError: (Throwable) -> Unit = {}
) {
    val observer = object : OkObserver<T>() {
        override fun onSubscribe(d: Disposable) {
            super.onSubscribe(d)
            dc.add(d)
        }

        override fun onSuccess(t: T) {
            super.onSuccess(t)
            onSuccess(t)
        }

        override fun onError(e: Throwable) {
            super.onError(e)
            onError(e)
        }
    }

    okSubscribe(observer)
}

/**
 * OkObserver 的形式定订阅
 */
fun <T> Single<T>.okSubscribe(okObserver: OkObserver<T>) {
    val packet = okPacketOf(this.hashCode())
    if (packet != null) {
        okObserver.attachWithPacket(packet)
        removePacketOf(this.hashCode())
    }
    subscribe(okObserver)
}