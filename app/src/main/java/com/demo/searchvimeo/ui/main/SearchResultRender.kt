package com.demo.searchvimeo.ui.main

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.searchvimeo.R
import com.demo.searchvimeo.model.*
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

    // DES：recycleView adapter
    fun init(recyclerView: RecyclerView, onItemClick: (String) -> Unit) {
        this.recyclerView = recyclerView
        recyclerView.setup<Any> {
            dataSource(mutableListOf())
            withLayoutManager { LinearLayoutManager(context) }
            adapter {
                addItem(R.layout.layout_video_item) {
                    isForViewType { data, _ -> data is SearchResult }
                    bindViewHolder { data, pos, _ ->
                        val imageView = findViewById<ImageView>(R.id.img)
                        (data as? SearchResult)?.let { dataSource ->
                            imageView.load(dataSource.imgUrl) {
                                placeHolderResId = R.drawable.ic_baseline_image_24
                            }
                            clicked(R.id.img) {
                                onItemClick.invoke(dataSource.videoUrl)
                            }
                        }
                    }
                }
                addItem(R.layout.layout_loading_item) {
                    isForViewType { data, _ -> data !is String }
                    bindViewHolder { data, _, _ ->
                        when (data) {
                            is Loading -> setText(R.id.abnormalText, "")
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

    // DES：submit dataSource
    fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> recyclerView.submitList(mutableListOf(Loading))
            is SearchState.Error -> recyclerView.submitList(mutableListOf(Error))
            is SearchState.Empty -> recyclerView.submitList(mutableListOf(Empty))
            is SearchState.Data -> recyclerView.submitList(state.result.toMutableList())
        }
    }
}