package com.smart.gankmvp.http

import com.smart.gankmvp.main.gank.GankDataBean
import com.smart.gankmvp.main.gank.MeiZiBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServer {

    @GET("data/福利/10/{page}")
    abstract fun getMeizhiData(@Path("page") page: Int): Observable<MeiZiBean>

    @GET("day/{year}/{month}/{day}")
    abstract fun getGankData(@Path("year") year: Int, @Path("month") month: Int, @Path("day") day: Int): Observable<GankDataBean>

    @GET("data/休息视频/10/{page}")
    abstract fun getVideoData(@Path("page") page: Int): Observable<VideoBean>
}