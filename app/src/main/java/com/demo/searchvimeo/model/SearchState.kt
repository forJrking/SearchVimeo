package com.demo.searchvimeo.model

import java.lang.Exception

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 4:02 下午
 */
sealed class SearchState {
    object Loading : SearchState()
    data class Data(val result: List<SearchResult>) : SearchState()
    object Empty : SearchState()
    data class Error(val exception: Exception) : SearchState()
}