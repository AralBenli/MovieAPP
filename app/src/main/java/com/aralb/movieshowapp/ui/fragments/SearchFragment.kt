package com.aralb.movieshowapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.MovieAdapter
import com.aralb.movieshowapp.databinding.FragmentSearchBinding
import com.aralb.movieshowapp.ui.MainActivity
import com.aralb.movieshowapp.ui.viewModels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchAdapter = MovieAdapter()
    lateinit var text: String
    lateinit var binding: FragmentSearchBinding

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).backNavigation(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        navigation()
        binding.searchView.setBackgroundColor(Color.WHITE)

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    text = query
                    fetchSearch()
                    return true
                }
                return false
            }
        }
        )
        collectSearch()
    }

    private fun fetchSearch() {
        viewModel.getSearch(text)
    }

    private fun collectSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.searchData.collect { data ->
                        if (data != null) {
                            binding.searchRecyclerView.adapter = searchAdapter
                            searchAdapter.addMovieList(data.movies)
                        }
                    }
                }
            }
        }
    }

    private fun navigation() {
        searchAdapter.clickMovie = {
            val bundle = Bundle()
            bundle.putInt("movieId", it.id.toInt())
            findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
        }
    }
}
























