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
import androidx.recyclerview.widget.LinearLayoutManager
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.MovieAdapter
import com.aralb.movieshowapp.adapters.RecyclerViewClickInterface
import com.aralb.movieshowapp.models.response.MovieResultItem
import com.aralb.movieshowapp.ui.viewModels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(), RecyclerViewClickInterface {

    private lateinit var searchAdapter: MovieAdapter
    private lateinit var linearlayoutmanager: LinearLayoutManager
    lateinit var text: String

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView.setBackgroundColor(Color.WHITE)

        view.searchView.setOnQueryTextListener(object :
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
                            searchAdapter = MovieAdapter(
                                requireContext(),
                                data.movies,
                                this@SearchFragment
                            )

                            searchRecyclerView.adapter = searchAdapter
                        }

                    }

                }
            }
        }
    }

    override fun onItemClicked(movie: MovieResultItem) {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
    }
}
























