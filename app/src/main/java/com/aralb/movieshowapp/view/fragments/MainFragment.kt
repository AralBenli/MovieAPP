package com.aralb.movieshowapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.RecyclerViewClickInterface
import com.aralb.movieshowapp.adapters.MovieAdapter
import com.aralb.movieshowapp.models.movieData.MovieResultItem
import com.aralb.movieshowapp.response.MovieResponse
import com.aralb.movieshowapp.view.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() , RecyclerViewClickInterface{

    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var upComingMovieAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter
    private lateinit var viewModelList: MainViewModel
    private lateinit var movie : MovieResultItem
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        viewModelList = ViewModelProvider(this)[MainViewModel::class.java]
        return view
    }

    override fun getView(): View? {
        return super.getView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.listScopeImageView.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_searchFragment)
        }

        // POPULAR MOVIES

        popularRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        popularRecyclerView.setHasFixedSize(true)

        val popularObserver = Observer<MovieResponse> { data ->

            popularMovieAdapter = MovieAdapter(
                requireContext(),
                data.movies,
                this
            )

            popularRecyclerView.adapter = popularMovieAdapter
        }

        viewModelList.popularMovieModel.observe(requireActivity(), popularObserver)


        // UPCOMING MOVIES


        upcomingRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        upcomingRecyclerView.setHasFixedSize(true)


        val upcomingMovieObserver = Observer<MovieResponse> { data ->

            upComingMovieAdapter = MovieAdapter(
                requireContext(),
                data.movies,
                this
            )

            upcomingRecyclerView.adapter = upComingMovieAdapter
        }
        viewModelList.upcomingMovieModel.observe(requireActivity(), upcomingMovieObserver)


        // TOP RATED MOVIES

        topRatedRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        topRatedRecyclerView.setHasFixedSize(true)


        val topRatedMovieObserver = Observer<MovieResponse> { data ->

            topRatedAdapter = MovieAdapter(
                requireContext(),
                data.movies,
                this
            )

            topRatedRecyclerView.adapter = topRatedAdapter
        }
        viewModelList.topRatedMovieModel.observe(requireActivity(), topRatedMovieObserver)

        viewModelList.getPopularMovieData()
        viewModelList.getUpcomingMovieData()
        viewModelList.getTopRated()

    }

    override fun onItemClicked(movie: MovieResultItem) {

        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        findNavController().navigate(R.id.action_MainFragment_to_detailsFragment, bundle)

    }
}



