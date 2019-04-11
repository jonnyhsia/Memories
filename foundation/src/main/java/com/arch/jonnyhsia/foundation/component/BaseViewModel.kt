package com.arch.jonnyhsia.foundation.component

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arch.jonnyhsia.compass.Compass
import com.arch.jonnyhsia.compass.RouteIntent
import com.arch.jonnyhsia.mirror.logger.Corgi
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import org.greenrobot.eventbus.EventBus

abstract class BaseViewModel : ViewModel(), Corgi, DisposableContainer {

    val superNavigator = SingleLiveEvent<RouteIntent>()

    internal val toaster = SingleLiveEvent<String>()
    val closeSignal = SingleLiveEvent<Unit>()

    val status = MutableLiveData<PageStatus>()

    private var isEventBusEnabled = false

    private var disposables: CompositeDisposable? = null

    fun enableEventBus() {
        if (!isEventBusEnabled) {
            EventBus.getDefault().register(this)
        }
    }

    fun disposables(): CompositeDisposable {
        if (disposables == null) {
            disposables = CompositeDisposable()
        }
        return disposables!!
    }

    protected inline fun navigate(
            url: String,
            close: Boolean = false,
            block: RouteIntent.() -> Unit = {}
    ) {
        superNavigator.value = Compass.navigate(url).apply(block)
        if (close) {
            closeSignal.call()
        }
    }

    protected fun toast(text: String?) {
        text ?: return
        toaster.value = text
    }

    override fun onCleared() {
        super.onCleared()
        disposables?.clear()
        if (isEventBusEnabled) {
            EventBus.getDefault().unregister(this)
        }
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

fun <T> Single<T>.xsubscribe(
        disposable: DisposableContainer,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit = {}
) {
    disposable.add(subscribe(onSuccess, onError))
}