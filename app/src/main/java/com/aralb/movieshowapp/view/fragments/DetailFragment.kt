package com.aralb.movieshowapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.MovieAdapter
import com.aralb.movieshowapp.adapters.RecyclerViewClickInterface
import com.aralb.movieshowapp.models.movieData.MovieResultItem
import com.aralb.movieshowapp.models.movieDetail.MovieDetail
import com.aralb.movieshowapp.models.response.MovieResponse
import com.aralb.movieshowapp.util.Constants.imageBase
import com.aralb.movieshowapp.view.viewModels.DetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*

@AndroidEntryPoint
class DetailFragment : Fragment() , RecyclerViewClickInterface {

    private lateinit var movie : MovieResultItem
    private lateinit var similarMovieAdapter: MovieAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL , false)
        similarMovieRecyclerView.layoutManager= linearLayoutManager
        similarMovieRecyclerView.setHasFixedSize(true)

        movie = requireArguments().getParcelable("movie")!!
        // MOVIE DETAILS

        viewModel.getDetails(movie.id.toInt())
        val detailObserver = Observer<MovieDetail>{ data ->
            viewBind(data)
        }
        viewModel.detailModel.observe(requireActivity(),detailObserver)


        //SIMILAR MOVIES

        viewModel.getSimilar(movie.id.toInt())

        val similarObserver = Observer<MovieResponse>{ data ->

            similarMovieAdapter = MovieAdapter(
                    requireContext(),
                    data.movies,
                    this@DetailFragment)

            similarMovieRecyclerView.adapter = similarMovieAdapter
        }

        viewModel.similarModel.observe(requireActivity(),similarObserver)
    }

    override fun onItemClicked(movie : MovieResultItem){
        viewModel.getDetails(movie.id.toInt())
        detailScrollView.smoothScrollTo(0,0)}


    private fun viewBind(data: MovieDetail){

    Picasso.get()
        .load(imageBase + data.poster_path)
        .into(detailImageView)

    view?.detailOverview?.text = data.overview
    view?.detailTitleTextView?.text=data.title
    view?.detailBudgetTextView?.text= data.budget.toString()
    view?.detailOriginalLanguage?.text=data.original_language
    view?.detailOriginalTitle?.text = data.original_title
    view?.detailPopularity?.text = data.popularity.toString()
    view?.detailRatingbar?.rating=(data.vote_average.toFloat())/2

    viewModel.getSimilar(movie.id.toInt())
    }
}





