package com.jonnyhsia.memories.page.main.timeline

import androidx.lifecycle.MutableLiveData
import com.arch.jonnyhsia.foundation.component.BaseViewModel
import com.arch.jonnyhsia.foundation.component.xsubscribe
import com.arch.jonnyhsia.memories.model.Repository

class TimelineViewModel : BaseViewModel() {

    val timeline = MutableLiveData<List<Any>>()

    init {
        Repository.getStoryDataSource().getTimeline(page = 0)
                .xsubscribe(this, onSuccess = {
                    timeline.value = it
                }, onError = {
                    toast(it.message)
                })
    }
}