package com.demo.searchvimeo.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.demo.searchvimeo.MainViewModel
import com.demo.searchvimeo.databinding.MainFragmentBinding
import com.demo.searchvimeo.viewmodel.SearchFragmentViewModel
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import com.demo.searchvimeo.di.RepositoryModule.BASEURL


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var searchResultRender: SearchResultRender

    @Inject
    lateinit var searchBarRender: SearchBarRender

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SearchFragmentViewModel by viewModels { viewModelFactory }

    // share data or
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return MainFragmentBinding.inflate(inflater, container, false).run {
            binding = this
            ////region ========== init Render ==========
            searchResultRender.init(binding.recyclerView) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                try {
                    val uri: Uri = Uri.parse("$BASEURL$it")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), " Please Install browser", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            searchBarRender.init(binding.searchBar) {
                viewModel.query(it)
            }
            //endregion
            root
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // di code
        try {
            mainViewModel.component?.inject(this)
        } catch (e: Exception) {
            requireActivity().finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // lifeData observe
        viewModel.stateLifeData.observe(viewLifecycleOwner) {
            // dispatcher state
            searchBarRender.render(it)
            searchResultRender.render(it)
        }
    }

}