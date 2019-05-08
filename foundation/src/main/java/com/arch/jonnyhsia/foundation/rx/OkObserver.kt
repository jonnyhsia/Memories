package com.arch.jonnyhsia.foundation.rx

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

abstract class OkObserver<T> : SingleObserver<T> {

    private var emptyView: MutableLiveData<Unit?>? = null
    private var refreshLayout: MutableLiveData<Unit?>? = null
    private var progressDialog: MutableLiveData<Unit?>? = null

    @CallSuper
    override fun onSubscribe(d: Disposable) {
        // 显示刷新与进度条
        refreshLayout.callIfRealChanged(Unit)
        progressDialog.callIfRealChanged(Unit)
    }

    @CallSuper
    override fun onSuccess(t: T) {
        // 隐藏空布局, 刷新与进度条
        emptyView.callIfRealChanged()
        refreshLayout.callIfRealChanged()
        progressDialog.callIfRealChanged()
    }

    @CallSuper
    override fun onError(e: Throwable) {
        // 显示空布局, 隐藏刷新与进度条
        emptyView.callIfRealChanged(Unit)
        refreshLayout.callIfRealChanged()
        progressDialog.callIfRealChanged()
    }

    fun attachWithPacket(okPacket: OkTempPacket) {
        emptyView = okPacket.emptyView
        refreshLayout = okPacket.refreshLayout
        progressDialog = okPacket.progressDialog
    }
}

fun <T> Single<T>.okSubscribe(okObserver: OkObserver<T>) {
    val packet = okPacketOf(this.hashCode())
    if (packet != null) {
        okObserver.attachWithPacket(packet)
        removePacketOf(this.hashCode())
    }
    subscribe(okObserver)
}

private fun <T> MutableLiveData<T>?.callIfRealChanged(newValue: T? = null) {
    if (this != null && value != newValue) {
        this.value = newValue
    }
}