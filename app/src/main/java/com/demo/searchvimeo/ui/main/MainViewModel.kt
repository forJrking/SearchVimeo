package com.demo.searchvimeo.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.searchvimeo.model.ModelReducer
import com.demo.searchvimeo.model.SearchState
import com.demo.searchvimeo.repo.RepositoryModule
import com.demo.searchvimeo.repo.Result
import com.demo.searchvimeo.repo.SearchVideoApi
import com.demo.searchvimeo.repo.asResult
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val stateLifeData = MutableLiveData<SearchState>()

    private val reducer = ModelReducer()

    private val repo: SearchVideoApi by lazy {
        RepositoryModule.providesRepository(RepositoryModule.createOkhttpClient())
    }

    fun query(it: String?) {
        if (it.isNullOrBlank()) {
            stateLifeData.value = SearchState.Empty
        } else {
            viewModelScope.launch {
                stateLifeData.postValue(SearchState.Loading)
                val result = asResult {
                    repo.fetchSearchVimeoResult(it).string()
                }

                when (result) {
                    is Result.Data -> {
                        val data = result.data
                        if (data.isNullOrBlank()) {
                            stateLifeData.postValue(SearchState.Empty)
                        } else {
                            stateLifeData.postValue(SearchState.Data(
                                result = reducer.reducer(data)
                            ))
                        }
                    }
                    is Result.Error -> {
                        stateLifeData.postValue(SearchState.Error(result.exception))
                    }
                }
            }
        }
    }
}