package com.demo.searchvimeo.model

import android.util.Log
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
            "<a class=\"item_thumb\" href=\".*?\">\\s+<img src=\".*?\">\\s+<span.*?</a>"
        private const val regImg = "srcset=\"http.*?\\s3x\""
    }

    private fun matchList(data: String): List<String> = mutableListOf<String>().apply {
        val matcher = Pattern.compile(rowReg).matcher(data.trim())
        try {
            while (matcher.find()) {
                val group = matcher.group()
                Log.d("matcher", group)
                val matcherSrc = Pattern.compile(regImg).matcher(group)
                while (matcherSrc.find()) {
                    val group1 = matcherSrc.group()
                    val imgUrl = group1.replace("srcset=\"", "")
                        .replace("\"", "")
                        .replace(" 2x", "")
                        .replace(" 3x", "")
                        .split(",")[0]
                    Log.d("matcher1", imgUrl)
                    this.add(imgUrl)
                }
            }
        } catch (e: Exception) {
        }
    }


}