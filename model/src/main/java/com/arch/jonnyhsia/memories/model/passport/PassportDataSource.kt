package com.arch.jonnyhsia.memories.model.passport

import com.arch.jonnyhsia.memories.model.DataSource
import com.arch.jonnyhsia.memories.model.passport.bean.LevelInfoModel
import com.arch.jonnyhsia.memories.model.passport.bean.LoginType
import com.arch.jonnyhsia.memories.model.passport.bean.UserModel
import io.reactivex.Single

interface PassportDataSource : DataSource {

    fun getLoginUser(): UserModel?

    fun logout()

    fun checkUserLoginEntry(email: String): Single<LoginType>

    fun loginWithCode(email: String, password: String): Single<UserModel>

    fun register(email: String, password: String): Single<UserModel>

    fun getLevelInfo(): Single<LevelInfoModel>
}