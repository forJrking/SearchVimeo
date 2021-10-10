package com.demo.searchvimeo.model

import com.demo.searchvimeo.repo.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 5:15 下午
 */
class ModelReducer @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
) {

    // DES：把数据转换成View状态
    suspend fun reducer(result: Result<String>): SearchState = withContext(dispatcher) {
        when (result) {
            is Result.Data -> {
                matchList(result.data).run {
                    if (isEmpty()) {
                        SearchState.Empty
                    } else {
                        SearchState.Data(result = this)
                    }
                }
            }
            is Result.Error -> {
                SearchState.Error(result.exception)
            }
        }
    }


    companion object {

        private const val rowReg =
            "<a class=\"item_thumb\" href=\"(.*?)\">\\s+<img src=\".*?\">\\s+<span.*?</a>"
        private const val regImg = ".* srcset=\"(.*?) 2x, (.*?) 3x\".*"

    }

    // DES：reducer list
    private fun matchList(data: String) = mutableListOf<SearchResult>().apply {
        if (data.isNotEmpty()) {
            val matcher = Pattern.compile(rowReg).matcher(data.trim())
            try {
                // match row include img,videoUrl
                while (matcher.find()) {
                    val group = matcher.group()
                    val videoUrl = matcher.group(1)
                    println("url: $videoUrl")
                    val srcSetPattern = Pattern.compile(regImg)
                    val serSetMatcher = srcSetPattern.matcher(group)
                    while (serSetMatcher.find()) {
                        //match img  1x 2x 3x
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


}