package com.jonnyhsia.memories.page.main.account

import com.arch.jonnyhsia.memories.model.event.LoginEvent
import com.arch.jonnyhsia.memories.model.passport.PassportDataSource
import com.arch.jonnyhsia.mirror.logger.logd
import com.jonnyhsia.appcore.component.BaseViewModel
import com.jonnyhsia.appcore.livebus.LiveBus

class AccountViewModel(
        private val passportDataSource: PassportDataSource
) : BaseViewModel() {

    init {
        fetchUserInfo()
    }

    private fun fetchUserInfo() {

    }
}