package com.demo.searchvimeo.di

import androidx.lifecycle.ViewModel
import com.demo.searchvimeo.viewmodel.SearchFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 7:31 下午
 */
@Module
interface SearchViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchFragmentViewModel::class)
    fun bindViewModel(viewModel: SearchFragmentViewModel): ViewModel
}