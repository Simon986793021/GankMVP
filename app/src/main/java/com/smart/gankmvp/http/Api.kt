package com.smart.gankmvp.http

import com.smart.gankmvp.http.ApiConstant.DAILY_BASE_URL
import com.smart.gankmvp.http.ApiConstant.GANK_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory


object Api {

//    private var zhihuApiSingleton: ZhihuApi? = null
    private var gankApiSingleton: GankApi? = null
    private var dailyApiSingleton: DailyApi? = null
    private val client = OkHttpClient.Builder()
    //return Singleton
//    fun getZhihuApiSingleton(): ZhihuApi {
//        if (zhihuApiSingleton == null) {
//            synchronized(ZhihuApi::class.java) {
//                if (zhihuApiSingleton == null) {
//                    val retrofit_zhihu = Retrofit.Builder()
//                        .baseUrl(ZHIHU_BASE_URL)
//                        .client(OkHttpManager.getInstance())
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                        .build()
//                    zhihuApiSingleton = retrofit_zhihu.create(ZhihuApi::class.java!!)
//                }
//            }
//        }
//        return zhihuApiSingleton
//    }

    fun getGankApiSingleton(): GankApi? {

        if (gankApiSingleton == null) {
            synchronized(GankApi::class.java) {
                if (gankApiSingleton == null) {
                    val retrofit_gank = Retrofit.Builder()
                        .baseUrl(GANK_BASE_URL)
                        .client(client.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build()
                    gankApiSingleton = retrofit_gank.create(GankApi::class.java)
                }
            }
        }
        return this.gankApiSingleton
    }

    fun getDailyApiSingleton(): DailyApi? {
        if (dailyApiSingleton == null) {
            synchronized(DailyApi::class.java) {
                if (dailyApiSingleton == null) {
                    val retrofit_daily = Retrofit.Builder()
                        .baseUrl(DAILY_BASE_URL)
                        .client(client.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build()
                    dailyApiSingleton = retrofit_daily.create(DailyApi::class.java)
                }
            }
        }
        return dailyApiSingleton
    }
}
