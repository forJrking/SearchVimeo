package com.demo.searchvimeo.repo

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 3:29 下午
 */
@Module
object RepositoryModule {

    @Provides
    fun providesDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun providesRepository(okHttpClient: OkHttpClient): SearchVideoApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://vimeo.com")
            .client(okHttpClient)
            .build().let {
                it.create(SearchVideoApi::class.java)
            }

    @Provides
    fun createOkhttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
//        .cache(Cache(cacheDir, cacheSize))
        .build()

}