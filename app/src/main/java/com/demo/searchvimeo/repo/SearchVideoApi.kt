package com.demo.searchvimeo.repo

import retrofit2.http.GET

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 3:25 下午
 */
interface SearchVideoApi {

    @GET("search?q={searchTxt}")
    suspend fun fetchSearchVimeoResult(searchTxt: String): String

}