package com.demo.searchvimeo.di

import android.content.Context
import com.demo.searchvimeo.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 4:35 下午
 */
@Singleton
@Component(modules = [RepositoryModule::class, ViewModelFactoryModule::class, SearchViewModelModule::class])
interface SearchComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): SearchComponent
    }

    fun inject(fragment: MainFragment)

}