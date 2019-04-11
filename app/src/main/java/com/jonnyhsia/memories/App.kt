package com.jonnyhsia.memories

import android.app.Application
import android.graphics.Typeface
import android.os.Looper
import androidx.core.content.res.ResourcesCompat
import com.arch.jonnyhsia.compass.Compass
import com.arch.jonnyhsia.foundation.Foundation
import com.arch.jonnyhsia.foundation.ext.isMainProcess
import com.arch.jonnyhsia.memories.model.Repository
import com.arch.jonnyhsia.mirror.bugnets.Bugnets
import com.arch.jonnyhsia.mirror.logger.Corgi
import com.facebook.stetho.Stetho
import com.jonnyhsia.memories.router.LoginInterceptor
import com.jonnyhsia.memories.router.WebInterceptor
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.crashreport.CrashReport
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.properties.Delegates

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        // 判断主进程, 并初始化组件
        if (!isMainProcess()) {
            return
        }

        RxAndroidPlugins.setMainThreadSchedulerHandler {
            AndroidSchedulers.from(Looper.getMainLooper(), true)
        }
        Foundation.attachApplication(this)
        setupKoin()
        setupModel()
        EventBus.builder().addIndex(AppEventBusIndex()).installDefaultEventBus()
        setupCompass()
        setupMirror()
        setupDebugUtil()
    }

    private fun setupModel() {
        Repository.init(applicationContext, mapOf(
                "app_version" to BuildConfig.VERSION_NAME,
                "app_name" to "Memories"
        ))
    }

    /**
     * 初始化 Koin
     */
    private fun setupKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(appModule, mvvmModule)
        }
    }

    /**
     * 初始化调试工具
     */
    private fun setupDebugUtil() {
        Stetho.initializeWithDefaults(this)
        CrashReport.initCrashReport(this, "71bf918a20", BuildConfig.DEBUG)

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this)
        }
    }

    /**
     * 配置日志打印与异常处理
     */
    private fun setupMirror() {
        Corgi.loggable = BuildConfig.DEBUG
        Bugnets.handleException(applicationContext) {
            it.printStackTrace()
            return@handleException true
        }
    }

    /**
     * 配置路由
     */
    private fun setupCompass() {
        Compass.run {
            initialize(AppTable())
            setSchemeInterceptor(WebInterceptor)
            addRouteInterceptor(LoginInterceptor)
        }
    }

    companion object {

        var INSTANCE: App by Delegates.notNull()
            private set

        val NotoSansMedium: Typeface by lazy {
            ResourcesCompat.getFont(INSTANCE, R.font.noto_sans)!!
        }
    }
}