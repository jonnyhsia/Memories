package com.arch.jonnyhsia.foundation.rx

import androidx.collection.SparseArrayCompat
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single

/**
 * 用于临时保存封装信息的数组
 */
internal val okTempPacketArray = SparseArrayCompat<OkTempPacket>()

internal fun okPacketOf(hash: Int): OkTempPacket? {
    return okTempPacketArray.get(hash)
}

internal fun removePacketOf(hash: Int) {
    okTempPacketArray.remove(hash)
}

fun <T> Single<T>.attachEmptyView(liveData: MutableLiveData<Unit?>?): Single<T> {
    val packet = okTempPacketArray.get(this.hashCode()) ?: OkTempPacket()
    packet.emptyView = liveData
    okTempPacketArray.put(this.hashCode(), packet)
    return this
}

fun <T> Single<T>.attachRefreshLayout(liveData: MutableLiveData<Unit?>?): Single<T> {
    val packet = okTempPacketArray.get(this.hashCode()) ?: OkTempPacket()
    packet.refreshLayout = liveData
    okTempPacketArray.put(this.hashCode(), packet)
    return this
}

fun <T> Single<T>.attachProgressDialog(liveData: MutableLiveData<Unit?>?): Single<T> {
    val packet = okTempPacketArray.get(this.hashCode()) ?: OkTempPacket()
    packet.progressDialog = liveData
    okTempPacketArray.put(this.hashCode(), packet)
    return this
}

class OkTempPacket {
    internal var emptyView: MutableLiveData<Unit?>? = null
    internal var refreshLayout: MutableLiveData<Unit?>? = null
    internal var progressDialog: MutableLiveData<Unit?>? = null
}