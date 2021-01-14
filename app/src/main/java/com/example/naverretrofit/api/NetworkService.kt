package com.example.naverretrofit.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private const val NAVER_API_URL = "https://openapi.naver.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("X-Naver-Client-Id", "4htz3Due_zl0kEOqf1yV")
                .addHeader("X-Naver-Client-Secret", "ylIahW3W7K")
                .build()
            it.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(NAVER_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val naverApi: NaverApi = retrofit.create(NaverApi::class.java)
}