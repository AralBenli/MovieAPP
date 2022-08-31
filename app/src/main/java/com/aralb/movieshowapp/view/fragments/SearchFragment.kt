package com.aralb.movieshowapp.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.SearchAdapter
import com.aralb.movieshowapp.response.MovieResponse
import com.aralb.movieshowapp.api.AppModule
import com.aralb.movieshowapp.util.Constants
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {
    lateinit var searchAdapter: SearchAdapter
    lateinit var linearlayoutmanager: LinearLayoutManager
    lateinit var text: String

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

                    getSearch()
                    return true
                }
                return false
            } }) }

    fun getSearch() {
        val retrofit = AppModule.retrofitService.getSearch(Constants.api_key,text)
        retrofit.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {

        val responseBody = response.body()!!

                searchAdapter = SearchAdapter(
            requireContext(),
            responseBody.movies,
            findNavController()
        )

        searchRecyclerView.adapter = searchAdapter
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            } })
    }
}


















