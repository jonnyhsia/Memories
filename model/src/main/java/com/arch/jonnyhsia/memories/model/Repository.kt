package com.arch.jonnyhsia.memories.model

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.arch.jonnyhsia.memories.model.group.GroupDataSource
import com.arch.jonnyhsia.memories.model.group.GroupRepository
import com.arch.jonnyhsia.memories.model.msic.MiscDataSource
import com.arch.jonnyhsia.memories.model.msic.MiscRepository
import com.arch.jonnyhsia.memories.model.passport.PassportDataSource
import com.arch.jonnyhsia.memories.model.passport.PassportRepository
import com.arch.jonnyhsia.memories.model.passport.bean.UserModel
import com.arch.jonnyhsia.memories.model.story.StoryDataSource
import com.arch.jonnyhsia.memories.model.story.StoryRepository
import com.arch.jonnyhsia.memories.model.trophy.TrophyDataSource
import com.arch.jonnyhsia.memories.model.trophy.TrophyRepository
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.koinApplication
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

abstract class Repository {

    protected val context: Context
        get() = Repository.contextRef.get()!!

    protected val retrofit: Retrofit
        get() = Repository.retrofit

    protected inline fun <reified T> inflateApi(): T {
        return this.retrofit.create(T::class.java)
    }

    protected fun sharedPreferenceOf(name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    /**
     * 比较两个字符串版本号
     * 前者大则返回 1, 后者大则返回 -1, 相等则返回 0
     * 无法比较时返回 [Int.MIN_VALUE]
     */
    @Throws(NumberFormatException::class)
    protected fun String.compareVersion(another: String): Int {
        if ("Unknown".equals(this, true) || "Unknown".equals(another, true)) {
            return Int.MIN_VALUE
        }

        val anotherCodes = another.split(",")
        this.split(",").forEachIndexed { index, s ->
            val code = s.toInt()
            val anotherCode = anotherCodes[index].toInt()
            if (code > anotherCode) {
                return 1
            } else if (code < anotherCode) {
                return -1
            }
        }

        return 0
    }

    companion object {

        private lateinit var retrofit: Retrofit

        private lateinit var contextRef: WeakReference<Context>

        private lateinit var properties: Map<String, Any>

        @JvmStatic
        fun init(context: Context, properties: Map<String, Any>) {
            contextRef = WeakReference(context.applicationContext)
            this.properties = properties

            val httpClient = OkHttpClient.Builder()
                    .connectTimeout(6000, TimeUnit.MILLISECONDS)
                    .readTimeout(6000, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(StethoInterceptor())
                    .addInterceptor(customQueryStringInterceptor)
                    .build()

            retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
                    .baseUrl("https://api.tugou.com/")
                    .client(httpClient)
                    .build()

            preloadDataSource()
        }

        private fun preloadDataSource() {
            koinApplication {
                loadKoinModules(prefModule)
            }
        }

        private fun createMoshi(): Moshi {
            // TODO
            return Moshi.Builder().build()
        }

        /**
         * 添加QueryString参数
         */
        private val customQueryStringInterceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val httpUrlBuilder = originalHttpUrl.newBuilder()

            // 如果用户登录了，将user_id, token带上
            val user = loginUser()
            if (user != null) {
                httpUrlBuilder.addQueryParameter("user_id", user.id.toString())
            }

            val httpUrl = httpUrlBuilder.build()
            // 统一请求to
            val requestBuilder = original.newBuilder()
                    .addHeader("UUID", deviceId())
                    .addHeader("APP_NAME", appName())
                    .removeHeader("USER-AGENT")
                    // memories/android/2.0.0/API29
                    .addHeader("USER-AGENT", "${appName()}/android/${appVersion()}/API${Build.VERSION.SDK_INT}")
                    .url(httpUrl)

            val request = requestBuilder.build()
            return@Interceptor chain.proceed(request)
        }

        fun getPassportDataSource(): PassportDataSource {
            return PassportRepository
        }

        fun getMiscDataSource(): MiscDataSource {
            return MiscRepository
        }

        fun getTrophyDataSource(): TrophyDataSource {
            return TrophyRepository
        }

        fun getGroupDataSource(): GroupDataSource {
            return GroupRepository
        }

        internal fun appVersion(): String {
            return properties["app_version"] as? String ?: "Unknown"
        }

        internal fun appName(): String {
            return properties["app_name"] as? String ?: "Unknown"
        }

        /**
         * 获取设备号
         */
        @JvmStatic
        internal fun deviceId(): String {
            return Repository.getMiscDataSource().getDeviceID()
        }

        @JvmStatic
        internal fun loginUser(): UserModel? {
            return Repository.getPassportDataSource().getLoginUser()
        }

        fun getStoryDataSource(): StoryDataSource {
            return StoryRepository
        }
    }
}