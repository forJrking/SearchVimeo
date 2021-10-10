package com.demo.searchvimeo.ui.main

import android.view.View
import androidx.appcompat.widget.SearchView
import com.demo.searchvimeo.R
import com.demo.searchvimeo.model.SearchState
import javax.inject.Inject

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 3:51 下午
 */
class SearchBarRender @Inject constructor() {

    private lateinit var search: SearchView

    fun init(search: SearchView, onQuery: (String?) -> Unit) {
        this.search =search
        this.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onQuery.invoke(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }


    fun render(state: SearchState) {
        val enable = state !is SearchState.Loading
        search.findViewById<View>(R.id.search_src_text)?.let {
            it.isEnabled = enable
            it.isClickable = enable
        }
    }
}