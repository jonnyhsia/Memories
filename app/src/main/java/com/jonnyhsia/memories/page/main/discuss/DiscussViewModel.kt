package com.jonnyhsia.memories.page.main.discuss

import androidx.lifecycle.MutableLiveData
import com.arch.jonnyhsia.foundation.component.BaseViewModel
import com.arch.jonnyhsia.foundation.component.xsubscribe
import com.arch.jonnyhsia.memories.model.group.GroupDataSource

class DiscussViewModel(
        private val groupDataSource: GroupDataSource
): BaseViewModel() {

    val discussList = MutableLiveData<List<Any>>()

    init {
        groupDataSource.getList()
                .xsubscribe(this, onSuccess = {
                    discussList.value = it
                })
    }
}