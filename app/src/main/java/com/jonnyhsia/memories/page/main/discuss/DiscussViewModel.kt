package com.jonnyhsia.memories.page.main.discuss

import androidx.lifecycle.MutableLiveData
import com.arch.jonnyhsia.memories.model.group.GroupDataSource
import com.jonnyhsia.appcore.component.BaseViewModel
import com.jonnyhsia.appcore.okrx.attachLoadingView
import com.jonnyhsia.appcore.okrx.okSubscribe

class DiscussViewModel(
        private val groupDataSource: GroupDataSource
) : BaseViewModel() {

    val discussList = MutableLiveData<List<Any>>()

    init {
        groupDataSource.getList()
                .attachLoadingView(loadingView) {
                    discussList.value == null
                }
                .okSubscribe(this, onSuccess = {
                    discussList.value = it
                })
    }
}