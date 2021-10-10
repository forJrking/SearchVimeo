package com.demo.searchvimeo.di

import android.content.Context
import com.demo.searchvimeo.repo.SearchVideoApi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import retrofit2.Retrofit
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

    const val BASEURL = "https://vimeo.com"

    @Provides
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun providesRepository(okHttpClient: OkHttpClient): SearchVideoApi =
        Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .build().create(SearchVideoApi::class.java)

    @Provides
    fun createOkhttpClient(context: Context) = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.HEADERS) })
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .cache(Cache(context.cacheDir, 500 * 1024))
        .build()

}