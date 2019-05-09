package com.jonnyhsia.memories.page.main.discuss

import androidx.lifecycle.MutableLiveData
import com.arch.jonnyhsia.memories.model.group.GroupDataSource
import com.jonnyhsia.appcore.component.BaseViewModel
import com.jonnyhsia.appcore.okrx.okSubscribe

class DiscussViewModel(
        private val groupDataSource: GroupDataSource
): BaseViewModel() {

    val discussList = MutableLiveData<List<Any>>()

    init {
        groupDataSource.getList()
                .okSubscribe(this, onSuccess = {
                    discussList.value = it
                })
    }
}