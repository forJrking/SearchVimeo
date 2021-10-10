package com.demo.searchvimeo.ui.main

import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 3:51 下午
 */
class SearchResultRender @Inject constructor() {

    private lateinit var recyclerView: RecyclerView

    fun init(recyclerView: RecyclerView, onItemClick: (String) -> Unit) {

    }
}