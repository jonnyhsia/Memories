package com.jonnyhsia.memories.page.welcome

import com.arch.jonnyhsia.foundation.component.BaseViewModel
import com.arch.jonnyhsia.foundation.component.SingleLiveEvent
import com.arch.jonnyhsia.memories.model.msic.MiscDataSource
import com.arch.jonnyhsia.memories.model.trophy.TrophyDataSource
import com.arch.jonnyhsia.memories.model.trophy.bean.Trophy
import com.jonnyhsia.memories.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class WelcomeViewModel(
        private val miscDataSource: MiscDataSource,
        private val trophyDataSource: TrophyDataSource
) : BaseViewModel() {

    private var trickClickCount = 0
    private var countDownDisposable: Disposable? = null
    val trophySignal = SingleLiveEvent<Trophy>()

    val bannerList = listOf(R.drawable.img_welcome_01)

    init {
        miscDataSource.letWelcomePageEntered(false)
    }

    /**
     * 开始倒计时, 倒计时结束点击计数清零
     */
    private fun startCountDown() {
        countDownDisposable = Observable.interval(2, 2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    trickClickCount = 0
                }
    }

    /**
     * 检查当前点击次数
     */
    private fun checkTrickCount() {
        if (trickClickCount >= 5) {
            trickClickCount = -1
            countDownDisposable?.dispose()
            miscDataSource.letWelcomePageEntered()
            checkTrophy()
            navigate(url = "memo://Main", close = true)
        }
    }

    private fun checkTrophy() {
        // fixme: ugly code
        val trophy = trophyDataSource.earnTrophy(id = 1) ?: return
        trophySignal.value = trophy
    }

    fun onClickTrick() {
        if (trickClickCount == -1) {
            return
        }
        if (countDownDisposable == null) {
            startCountDown()
        }
        trickClickCount++
        checkTrickCount()
    }

    fun onClickAgree() {
        toast("别点，点了就是无可奉告")
    }
}