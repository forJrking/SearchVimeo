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
                SearchState.Data(
                    result = matchList(data)
                )
            }
        }
        is Result.Error -> {
            SearchState.Error(result.exception)
        }
    }


    companion object {

        private val rowReg = "<div class=\"iris_p_infinite__item span-1\">.*?</div>"
        private val reg =
            "<a class=\"iris_video-vital__overlay iris_link-box iris_annotation-box iris_chip-box\".*?</a>"
        private val regImg = "src=\".*?\""
    }

    //<div class="iris_video-vital iris_video-vital--browse">
//    <a class="iris_video-vital__overlay iris_link-box iris_annotation-box iris_chip-box" href="https://vimeo.com/201281043">

    private fun matchList(data: String): List<String> {
        val matcher = Pattern.compile(rowReg).matcher(data)
        return mutableListOf<String>().apply {
            while (matcher.find()) {
                val group = matcher.group()
                Log.d("matcher", group)
                val matcherSrc = Pattern.compile(regImg).matcher(group)
                while (matcherSrc.find()) {
                    this.add(matcherSrc.group())
                }
            }
        }
    }


}