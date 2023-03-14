package com.readthefuckingmanual.fuckukk.data.source.remote.retrofit

import com.readthefuckingmanual.fuckukk.data.source.remote.service.ClientApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitConfig {
    private val base_url = "https://api.zackym.com/"
    private val http_client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }
    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(base_url)
            .client(http_client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val ApiService = retrofitBuilder.build().create(ClientApiService::class.java)
}