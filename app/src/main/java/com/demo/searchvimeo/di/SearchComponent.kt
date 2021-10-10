package com.demo.searchvimeo.di

import com.demo.searchvimeo.repo.RepositoryModule
import com.demo.searchvimeo.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 4:35 下午
 */
@Singleton
@Component(modules = [RepositoryModule::class])
interface SearchComponent {

    @Component.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun inject(fragment: MainFragment)

}