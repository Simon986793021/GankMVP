package com.smart.gankmvp.http

import com.smart.gankmvp.main.daily.DailyTimeLine
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface DailyApi {
    @GET("homes/index/{num}.json")
    fun getDailyTimeLine(@Path("num") num: String): Observable<DailyTimeLine>

    @GET("options/index/{id}/{num}.json")
    fun getDailyFeedDetail(@Path("id") id: String, @Path("num") num: String): Observable<DailyTimeLine>
}