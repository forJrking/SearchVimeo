package com.demo.searchvimeo.repo

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 3:25 下午
 */
interface SearchVideoApi {

    @GET("search?q=")
    suspend fun fetchSearchVimeoResult(@Query("searchTxt") searchTxt: String): ResponseBody

}