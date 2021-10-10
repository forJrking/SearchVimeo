package com.demo.searchvimeo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.searchvimeo.model.ModelReducer
import com.demo.searchvimeo.model.SearchState
import com.demo.searchvimeo.repo.SearchVideoApi
import com.demo.searchvimeo.repo.asResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(
    private val apiRepo: SearchVideoApi,
    private val reducer: ModelReducer,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    val stateLifeData = MutableLiveData<SearchState>()

    fun query(it: String?) {
        if (it.isNullOrBlank()) {
            stateLifeData.value = SearchState.Empty
        } else {
            viewModelScope.launch {
                stateLifeData.postValue(SearchState.Loading)
                //use IO thread
                val result = asResult(dispatcher = dispatcher) {
                    apiRepo.fetchSearchVimeoResult(it).string()
                }
                reducer.reducer(result).also(stateLifeData::postValue)
            }
        }
    }
}