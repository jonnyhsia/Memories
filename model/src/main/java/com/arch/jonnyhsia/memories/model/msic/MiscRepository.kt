package com.arch.jonnyhsia.memories.model.msic

import android.content.SharedPreferences
import androidx.core.content.edit
import com.arch.jonnyhsia.memories.model.Repository
import com.arch.jonnyhsia.memories.model.passport.PassportRepository
import com.arch.jonnyhsia.memories.model.passport.bean.UserModel
import java.util.*

object MiscRepository : Repository(), MiscDataSource {

    private var deviceId: String? = null

    private val misc: SharedPreferences by lazy {
        sharedPreferenceOf("misc")
    }

    override fun haveWelcomePageEntered(): Boolean {
        return misc.getString("enter_welcome_page_version", null) != null
    }


    override fun letWelcomePageEntered(entered: Boolean) {
        misc.edit {
            if (entered) {
                putString("enter_welcome_page_version", appVersion())
            } else {
                remove("enter_welcome_page_version")
            }
        }
    }

    override
    fun getDeviceID(): String {
        if (deviceId != null) {
            return deviceId!!
        }
        deviceId = UUID.randomUUID().toString()
        misc.edit {
            putString("device_id", deviceId)
        }
        return deviceId!!
    }

    override fun saveLatestUserInfo(user: UserModel) {
        misc.edit {
            putString("last_login_email", user.email)
            putString("last_login_name", user.nickname)
        }
    }
}