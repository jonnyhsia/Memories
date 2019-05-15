package com.jonnyhsia.appcore.component

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arch.jonnyhsia.compass.Compass
import com.arch.jonnyhsia.compass.RouteIntent
import com.arch.jonnyhsia.mirror.logger.Corgi
import com.jonnyhsia.appcore.okrx.OkNotification
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

abstract class BaseViewModel : ViewModel(), Corgi, DisposableContainer {

    val superNavigator = SingleLiveEvent<RouteIntent>()

    internal val toaster = SingleLiveEvent<String>()
    val closeSignal = SingleLiveEvent<Unit>()

    val emptyView: MutableLiveData<OkNotification?> by lazy {
        MutableLiveData<OkNotification?>()
    }
    val refreshLayout: MutableLiveData<OkNotification?> by lazy {
        MutableLiveData<OkNotification?>()
    }
    val loadingView: MutableLiveData<OkNotification?> by lazy {
        MutableLiveData<OkNotification?>()
    }

    private var disposables: CompositeDisposable? = null

    fun disposables(): CompositeDisposable {
        if (disposables == null) {
            disposables = CompositeDisposable()
        }
        return disposables!!
    }

//    protected inline fun navigate(
//            url: String,
//            close: Boolean = false,
//            block: RouteIntent.() -> Unit = {}
//    ) {
//        if (close) {
//            closeSignal.call()
//        }
//        superNavigator.value = Compass.navigate(url).apply(block)
//    }

    protected inline fun navigate(
            url: String,
            block: RouteIntent.() -> Unit = {}
    ) {
        superNavigator.value = Compass.navigate(url).apply(block)
    }

    protected fun close() {
        closeSignal.call()
    }

    protected fun toast(text: String?) {
        text ?: return
        toaster.value = text
    }

    override fun onCleared() {
        super.onCleared()
        disposables?.clear()
    }

    fun gotoLogin(entry: String) {
        navigate("memo://Login") {
            addParameter("entry", entry)
        }
    }

    override fun add(d: Disposable): Boolean {
        return disposables().add(d)
    }

    override fun remove(d: Disposable): Boolean {
        return disposables?.remove(d) ?: false
    }

    override fun delete(d: Disposable): Boolean {
        return disposables?.delete(d) ?: false
    }
}

fun <T> Observable<T>.xsubscribe(
        disposable: DisposableContainer,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = {},
        onComplete: () -> Unit = {}
) {
    disposable.add(subscribe(onNext, onError, onComplete))
}