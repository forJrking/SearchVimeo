package com.demo.searchvimeo.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.demo.searchvimeo.R
import com.demo.searchvimeo.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: MainFragmentBinding

    lateinit var searchResultRender: SearchResultRender
    lateinit var searchBarRender: SearchBarRender

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return MainFragmentBinding.inflate(inflater, container, false).run {
            binding = this
            searchResultRender = SearchResultRender()
            searchBarRender = SearchBarRender()
            searchResultRender.init(binding.recyclerView) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            searchBarRender.init(binding.searchBar) {
                viewModel.query(it)
            }
            root
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stateLifeData.observe(viewLifecycleOwner) {
            searchBarRender.render(it)
            searchResultRender.render(it)
        }
    }

}