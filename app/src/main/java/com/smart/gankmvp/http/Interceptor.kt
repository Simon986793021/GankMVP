package com.smartcentury.kcwork.http

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * Created by vincentkin038 on 2017/12/1.
 * okhttp response拦截器
 */
class Interceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        return if (chain != null) {
            val newRequest = chain.request()
            val response = chain.proceed(newRequest)
            if (response.body() != null && response.body()!!.contentType() != null) {
                val mediaType = response.body()!!.contentType()
                val string = response.body()!!.string()
                println("---------------->>>  $string")
                val responseBody = ResponseBody.create(mediaType, string)
                return response.newBuilder().body(responseBody).build()
            } else {
                response
            }

        } else {
            null
        }
    }
}