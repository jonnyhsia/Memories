package com.jonnyhsia.memories.page.main.account.storylist

import androidx.lifecycle.MutableLiveData
import com.arch.jonnyhsia.memories.model.passport.PassportDataSource
import com.arch.jonnyhsia.memories.model.story.StoryDataSource
import com.jonnyhsia.appcore.component.BaseViewModel
import com.jonnyhsia.appcore.okrx.attachLoadingView
import com.jonnyhsia.appcore.okrx.okSubscribe

class StoryListViewModel(
        private val storyDataSource: StoryDataSource,
        private val passportDataSource: PassportDataSource
) : BaseViewModel() {

    val storyList = MutableLiveData<ArrayList<Any>>()

    private var currentPage = 0

    init {
        fetchStoryList()
        fetchLevelInfo()
    }

    private fun fetchLevelInfo() {
        passportDataSource.getLevelInfo()
                .okSubscribe(this, onSuccess = {
                    val list = storyList.value ?: ArrayList(16)
                    list.add(0, it)
                    storyList.value = list
                })
    }

    private fun fetchStoryList() {
        storyDataSource.getUserStories(userId = 0, page = currentPage + 1)
                .attachLoadingView(loadingView) {
                    storyList.value == null
                }
                .okSubscribe(this, onSuccess = {
                    currentPage += 1
                    val list = storyList.value ?: ArrayList(it.size + 1)
                    list.addAll(it)

                    storyList.value = list
                })
    }
}