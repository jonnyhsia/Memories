package com.arch.jonnyhsia.memories.model.passport

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.Observer
import com.arch.jonnyhsia.memories.model.Repository
import com.arch.jonnyhsia.memories.model.event.LoginEvent
import com.arch.jonnyhsia.memories.model.passport.bean.LevelInfoModel
import com.arch.jonnyhsia.memories.model.passport.bean.LoginType
import com.arch.jonnyhsia.memories.model.passport.bean.UserModel
import com.jonnyhsia.appcore.livebus.LiveBus
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

object PassportRepository : Repository(), PassportDataSource {
    // private val passportApi = inflateApi<PassportApi>()

    private val passport: SharedPreferences by lazy {
        sharedPreferenceOf("passport")
    }

    private var cachedUser: UserModel? = null

    override fun getLoginUser(): UserModel? {
        if (cachedUser != null) {
            return cachedUser
        }

        val userId = passport.getInt("id", -1)
        if (userId == -1) {
            return null
        }

        passport.run {
            val mobile = getString("mobile", "")!!
            // 若没有内存缓存, 尝试去 sp 中找
            val nickname = getString("nickname", "")!!
            val email = getString("email", "")!!
            val gender = getInt("gender", 0)
            val avatar = getString("avatar", "")!!

            cachedUser = UserModel(userId, nickname, mobile, email, avatar, gender)
            return cachedUser
        }
    }

    override fun logout() {
        if (cachedUser == null) {
            return
        }

        cachedUser = null
        passport.edit { clear() }
    }

    override fun checkUserLoginEntry(email: String): Single<LoginType> {
        val misc = sharedPreferenceOf("misc")
        val latestEmail = misc.getString("last_login_email", null)
        val latestUsername = misc.getString("last_login_name", null)

        if (latestEmail == null || latestUsername == null || latestEmail != email) {
            return Single.just<LoginType>(LoginType.Register())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        return Single.just<LoginType>(LoginType.LoginDirectly(latestUsername))
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun loginWithCode(email: String, password: String): Single<UserModel> {
        val name = email.substring(0, email.indexOf("@"))
        val user = UserModel(id = 0, nickname = name, email = email, gender = 1, avatar = "", mobile = "")
        savePassport(user)
        return Single.just(user)
    }

    override fun register(email: String, password: String): Single<UserModel> {
        if (password.filter { it != ' ' }.length < 6) {
            return Single.error(Exception("密码不短于6位"))
        }
        val name = email.substring(0, email.indexOf("@"))
        val user = UserModel(id = 0, nickname = name, email = email, gender = 1, avatar = "", mobile = "")
        savePassport(user)
        return Single.just(user)
    }

    private fun savePassport(user: UserModel) {
        passport.edit {
            putInt("id", user.id)
            putString("mobile", user.mobile)
            putString("nickname", user.nickname)
            putString("email", user.email)
            putInt("gender", user.gender)
            putString("avatar", user.avatar)
        }

        LiveBus.dispatch(LoginEvent(user))
        getMiscDataSource().run {
            letWelcomePageEntered()
            saveLatestUserInfo(user)
        }
    }

    override fun getLevelInfo(): Single<LevelInfoModel> {
        return Single.just(LevelInfoModel(levelQuote = "A memory a day, Keep the Amnesia away.",
                currentLevel = "LV2", nextLevel = "LV3", levelUpCondition = "再记录<font color='#FF2952'> 3 篇 </font>可升级至 LV3"))
    }
}