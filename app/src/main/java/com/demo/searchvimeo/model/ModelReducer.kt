package com.demo.searchvimeo.model

import com.demo.searchvimeo.repo.Result
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 5:15 下午
 */
class ModelReducer @Inject constructor() {

    fun reducer(result: Result<String>): SearchState = when (result) {
        is Result.Data -> {
            val data = result.data
            if (data.isBlank()) {
                SearchState.Empty
            } else {
                val resultList = matchList(data)
                if (resultList.isEmpty()) {
                    SearchState.Empty
                } else {
                    SearchState.Data(
                        result = resultList
                    )
                }
            }
        }
        is Result.Error -> {
            SearchState.Error(result.exception)
        }
    }


    companion object {

        private const val rowReg =
            "<a class=\"item_thumb\" href=\"(.*?)\">\\s+<img src=\".*?\">\\s+<span.*?</a>"
        private const val regImg = ".* srcset=\"(.*?) 2x, (.*?) 3x\".*"

    }

    private fun matchList(data: String) = mutableListOf<SearchResult>().apply {
        val matcher = Pattern.compile(rowReg).matcher(data.trim())
        try {
            while (matcher.find()) {
                val group = matcher.group()
                val videoUrl = matcher.group(1)
                println("url: $videoUrl")
                val srcSetPattern = Pattern.compile(regImg)
                val serSetMatcher = srcSetPattern.matcher(group)
                while (serSetMatcher.find()) {
                    println("2x: ${serSetMatcher.group(1)}")
                    val imgUrl = serSetMatcher.group(2)
                    println("3x: $imgUrl")
                    this.add(SearchResult(
                        imgUrl = imgUrl,
                        videoUrl = videoUrl
                    ))
                }
            }
        } catch (e: Exception) {
        }
    }


}