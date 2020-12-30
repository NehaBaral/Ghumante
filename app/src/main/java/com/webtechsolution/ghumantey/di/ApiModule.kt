package com.webtechsolution.ghumantey.di

import android.content.Context
import com.webtechsolution.ghumantey.app.Config
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ApiModule {
    @Singleton
    @Provides
    fun cache(context: Context): Cache {
        return Cache(File(context.cacheDir, "http_cache"), Config.okHttpCacheSizeMB);
    }

    @Provides
    @Singleton
    fun okHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(Config.okHttpConnectionTimeOut, TimeUnit.SECONDS)
        .readTimeout(Config.okHttpReadTimeOut, TimeUnit.SECONDS)
        .writeTimeout(Config.okHttpWriteTimeOut, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun apiInterface(okHttpClient: OkHttpClient): ApiInterface =
        Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiInterface::class.java)


    @Provides
    @Singleton
    fun tokenInterceptor(preferences: Preferences) = Interceptor.invoke {
        val original: Request = it.request()
        val builder: Request.Builder = original.newBuilder().method(original.method, original.body)
        builder.header("Accept", "application/json")
       // preferences.token?.let { token -> builder.header("Authorization", "Bearer $token") }
        it.proceed(builder.build())
    }


    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}