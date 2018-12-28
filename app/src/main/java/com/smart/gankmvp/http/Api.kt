package com.smartcentury.kcwork.http

import com.smart.gankmvp.http.ApiConstant
import com.smart.gankmvp.http.ApiServer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory


object Api {
    val apiServer: ApiServer

    init {
        val client = OkHttpClient.Builder()
        client.retryOnConnectionFailure(false)
        val logInterceptor = HttpLoggingInterceptor(LogInterceptor())
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addNetworkInterceptor(logInterceptor)
        val retrofit = Retrofit.Builder().baseUrl(ApiConstant.GANK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client.build()).build()
        apiServer = retrofit.create(ApiServer::class.java)
    }
}