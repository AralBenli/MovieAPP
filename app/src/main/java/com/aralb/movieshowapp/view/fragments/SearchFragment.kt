package com.aralb.movieshowapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.SearchAdapter
import com.aralb.movieshowapp.response.MovieResponse
import com.aralb.movieshowapp.view.viewModels.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {
    lateinit var searchAdapter: SearchAdapter
    lateinit var linearlayoutmanager: LinearLayoutManager
    lateinit var text: String
    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        return view
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
                    searchViewModel.getSearch(text)
                    return true
                }
                return false
            } })

        //SEARCH MOVIE
        val searchObserver = Observer<MovieResponse> { data ->
            searchAdapter = SearchAdapter(
                requireContext(),
                data.movies,
                findNavController()
            )
            searchRecyclerView.adapter = searchAdapter
        }
        searchViewModel.searchModel.observe(requireActivity(), searchObserver)
    } }



















