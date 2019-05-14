package com.jonnyhsia.memories.page.main.timeline

import androidx.lifecycle.MutableLiveData
import com.arch.jonnyhsia.memories.model.Repository
import com.jonnyhsia.appcore.component.BaseViewModel
import com.jonnyhsia.appcore.okrx.attachEmptyView
import com.jonnyhsia.appcore.okrx.attachLoadingView
import com.jonnyhsia.appcore.okrx.okSubscribe
import io.reactivex.Single


class TimelineViewModel : BaseViewModel() {

    private val storyDataSource = Repository.getStoryDataSource()
    val timeline = MutableLiveData<List<Any>>()

    init {
        fetchTimelineAt(page = 0)
    }

    private fun fetchTimelineAt(page: Int) {
        storyDataSource.getTimeline(page)
                .attachEmptyView(emptyView)
//                .attachLoadingView(loadingView)
                .okSubscribe(this, onSuccess = {
                    timeline.value = it
                })
    }

    fun xxxxxxxx() {
        if (emptyView.value == null) {
            toast("页面置为空白")
            timeline.value = emptyList()
            Single.error<Unit>(IllegalStateException())
                    .attachEmptyView(emptyView)
                    .okSubscribe(this, onSuccess = {})
        } else {
            toast("重新请求数据")
            fetchTimelineAt(1)
        }
    }
}