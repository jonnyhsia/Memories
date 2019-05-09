package com.arch.jonnyhsia.memories.model.msic

import com.arch.jonnyhsia.memories.model.DataSource
import com.arch.jonnyhsia.memories.model.passport.bean.UserModel

interface MiscDataSource : DataSource {

    fun getDeviceID(): String

    fun haveWelcomePageEntered(): Boolean

    fun letWelcomePageEntered(entered: Boolean = true)

    fun saveLatestUserInfo(user: UserModel)
}