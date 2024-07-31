package com.example.playground.retrofit

import com.example.playground.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// 싱글톤
object NetWorkClient {
    // Retrofit 사용 시 아래 틀은 바뀌지 않음
    // 대부분의 경우에 다음과 같이 사용한다.
    private const val DUST_BASE_URL = BuildConfig.DUST_BASE_URL

    // 통신이 잘 되지 않을 때 디버깅을 위한 용도
    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) interceptor.level = HttpLoggingInterceptor.Level.BODY
        else interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor).build()
    }

    private val dustRetrofit =
        Retrofit.Builder().baseUrl(DUST_BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(
                createOkHttpClient()
            ).build()

    val dustNetWork: NetWorkInterface = dustRetrofit.create(NetWorkInterface::class.java)

}