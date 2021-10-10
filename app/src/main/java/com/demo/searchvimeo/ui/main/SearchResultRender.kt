package com.demo.searchvimeo.ui.main

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.searchvimeo.R
import com.demo.searchvimeo.model.Empty
import com.demo.searchvimeo.model.Error
import com.demo.searchvimeo.model.Loading
import com.demo.searchvimeo.model.SearchState
import com.demo.searchvimeo.ui.recyclerview.*
import com.github.forjrking.image.load
import javax.inject.Inject

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 3:51 下午
 */
class SearchResultRender @Inject constructor() {

    private lateinit var recyclerView: RecyclerView

    fun init(recyclerView: RecyclerView, onItemClick: (String) -> Unit) {
        this.recyclerView = recyclerView
        recyclerView.setup<Any> {
            dataSource(mutableListOf())
            withLayoutManager { LinearLayoutManager(context) }
            adapter {
                addItem(R.layout.layout_video_item) {
                    isForViewType { data, _ -> data is String }
                    bindViewHolder { data, _, _ ->
                        findViewById<ImageView>(R.id.img).load(data)
                    }
                }
                addItem(R.layout.layout_loading_item) {
                    isForViewType { data, _ -> data !is String }
                    bindViewHolder { data, _, _ ->
                        when (data) {
                            is Loading -> setText(R.id.abnormalText, "Loading")
                            is Empty -> setText(R.id.abnormalText, "Empty")
                            is Error -> setText(R.id.abnormalText, "Error")
                        }
                        visibility(R.id.loading, if (data is Loading) View.VISIBLE else View.GONE)
                        visibility(R.id.abnormalText,
                            if (data !is Loading) View.VISIBLE else View.GONE)
                    }
                }
            }
        }
    }

    fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> recyclerView.submitList(mutableListOf(Loading))
            is SearchState.Error -> recyclerView.submitList(mutableListOf(Error))
            is SearchState.Empty -> recyclerView.submitList(mutableListOf(Empty))
            is SearchState.Data -> recyclerView.submitList(mutableListOf(state.result))
        }
    }
}