package com.aralb.movieshowapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.MovieAdapter
import com.aralb.movieshowapp.adapters.RecyclerViewClickInterface
import com.aralb.movieshowapp.models.movieData.MovieResultItem
import com.aralb.movieshowapp.models.response.MovieResponse
import com.aralb.movieshowapp.view.viewModels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*



@AndroidEntryPoint
class SearchFragment : Fragment() , RecyclerViewClickInterface {

    lateinit var searchAdapter: MovieAdapter
    lateinit var linearlayoutmanager: LinearLayoutManager
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

        linearlayoutmanager = LinearLayoutManager(requireContext())
        searchRecyclerView.layoutManager = linearlayoutmanager
        searchRecyclerView.setHasFixedSize(true)

        view.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    text = query
                    viewModel.getSearch(text)
                    return true
                }
                return false
            } })

        //SEARCH MOVIE

        val searchObserver = Observer<MovieResponse> { data ->
            searchAdapter = MovieAdapter(
                requireContext(),
                data.movies,
                this

            )
            searchRecyclerView.adapter = searchAdapter
        }
        viewModel.searchModel.observe(requireActivity(), searchObserver)

    }

    override fun onItemClicked(movie: MovieResultItem) {
           val bundle = Bundle()
           bundle.putParcelable("movie",movie)

        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment , bundle )
    }

}



















