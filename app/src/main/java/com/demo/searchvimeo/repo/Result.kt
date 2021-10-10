package com.demo.searchvimeo.repo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * @description: 数据包装
 * @author: forjrking
 * @date: 2021/10/10 3:19 下午
 */
sealed class Result<T : Any> {
    data class Data<T : Any>(val data: T) : Result<T>()
    data class Error<T : Any>(val exception: Exception) : Result<T>()
}

private val <T : Any> T.data get() = Result.Data(this)
private fun <T : Any> error(exception: Exception) = Result.Error<T>(exception)

suspend fun <T : Any> asResult(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> T,
): Result<T> = withContext(dispatcher) {
    try {
        block().data
    } catch (e: Exception) {
        error<T>(e)
    }
}