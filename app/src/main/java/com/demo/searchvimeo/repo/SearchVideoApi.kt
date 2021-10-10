package com.demo.searchvimeo.repo

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


/**
 * @description:API
 * @author: forjrking
 * @date: 2021/10/10 3:25 下午
 */
interface SearchVideoApi {

    @Headers("User-Agent: $getUserAgent")
    @GET("search")
    suspend fun fetchSearchVimeoResult(@Query("q") q: String): ResponseBody


    companion object {

        const val getUserAgent =
            "Mozilla/5.0 (Linux; Android 6.0.1; MI 4LTE Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/51.0.2704.81 Mobile Safari/537.36"

    }
}