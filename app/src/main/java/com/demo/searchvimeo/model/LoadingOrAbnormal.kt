package com.demo.searchvimeo.model

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 4:14 下午
 */
object Error

object Loading

object Empty

data class SearchResult(val imgUrl: String, val videoUrl: String)