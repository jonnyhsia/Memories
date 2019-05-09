package com.jonnyhsia.appcore.okrx

import androidx.collection.SparseArrayCompat
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single

/**
 * OkObserver 的临时封装信息
 */
class OkTempPacket {
    internal var emptyView: MutableLiveData<OkNotification?>? = null
    internal var refreshLayout: MutableLiveData<OkNotification?>? = null
    internal var progressDialog: MutableLiveData<OkNotification?>? = null
}

/**
 * 用于临时保存封装信息的数组
 */
internal val okTempPacketArray = SparseArrayCompat<OkTempPacket>()

/**
 * 根据 hash 从临时数组中找到对应的 temp packet
 */
internal fun okPacketOf(hash: Int): OkTempPacket? {
    return okTempPacketArray.get(hash)
}

/**
 * 根据 hash 从临时数组中移除相应的 temp packet
 */
internal fun removePacketOf(hash: Int) {
    okTempPacketArray.remove(hash)
}

/**
 * 临时保存 empty view 的引用
 */
fun <T> Single<T>.attachEmptyView(liveData: MutableLiveData<OkNotification?>?): Single<T> {
    val packet = okPacketOf(this.hashCode())
    if (packet == null) {
        val newPacket = OkTempPacket()
        newPacket.emptyView = liveData
        okTempPacketArray.put(this.hashCode(), newPacket)
    } else {
        packet.emptyView = liveData
    }

    return this
}

/**
 * 临时保存 refresh layout 的引用
 */
fun <T> Single<T>.attachRefreshLayout(liveData: MutableLiveData<OkNotification?>?): Single<T> {
    val packet = okPacketOf(this.hashCode())
    if (packet == null) {
        val newPacket = OkTempPacket()
        newPacket.refreshLayout = liveData
        okTempPacketArray.put(this.hashCode(), newPacket)
    } else {
        packet.refreshLayout = liveData
    }

    return this
}

/**
 * 临时保存 progress 的引用
 */
fun <T> Single<T>.attachProgressDialog(liveData: MutableLiveData<OkNotification?>?): Single<T> {
    val packet = okPacketOf(this.hashCode())
    if (packet == null) {
        val newPacket = OkTempPacket()
        newPacket.progressDialog = liveData
        okTempPacketArray.put(this.hashCode(), newPacket)
    } else {
        packet.progressDialog = liveData
    }

    return this
}