package com.jonnyhsia.memories

import android.app.Application
import android.os.Looper
import com.arch.jonnyhsia.compass.Compass
import com.arch.jonnyhsia.memories.model.Repository
import com.arch.jonnyhsia.mirror.bugnets.Bugnets
import com.arch.jonnyhsia.mirror.logger.Corgi
import com.facebook.stetho.Stetho
import com.jonnyhsia.appcore.AppCore
import com.jonnyhsia.appcore.ext.isMainProcess
import com.jonnyhsia.memories.router.LoginInterceptor
import com.jonnyhsia.memories.router.WebInterceptor
import com.jonnyhsia.memories.ui.showTrophyToast
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.crashreport.CrashReport
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.properties.Delegates

val application: App
    get() = App.INSTANCE

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
        setupAppCore()
        setupKoin()
        setupModel()
        setupCompass()
        setupMirror()
        setupDebugUtil()
    }

    private fun setupAppCore() {
        AppCore.attachApplication(this)
        // LiveBus.Config {
        //     container = BusContainerType.ArrayMap()
        // }
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

    fun checkTrophy(id: Int) {
        val trophy = Repository.getTrophyDataSource().earnTrophy(id)
                ?: return
        showTrophyToast(trophy)
    }

    companion object {
        var INSTANCE: App by Delegates.notNull()
            private set
    }
}