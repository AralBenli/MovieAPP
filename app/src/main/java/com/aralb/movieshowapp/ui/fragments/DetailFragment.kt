package com.aralb.movieshowapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.MovieAdapter
import com.aralb.movieshowapp.adapters.RecyclerViewClickInterface
import com.aralb.movieshowapp.models.response.MovieDetail
import com.aralb.movieshowapp.models.response.MovieResultItem
import com.aralb.movieshowapp.ui.viewModels.DetailViewModel
import com.aralb.movieshowapp.util.Constants.imageBase
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() , RecyclerViewClickInterface {

    private lateinit var movie : MovieResultItem
    private lateinit var similarMovieAdapter: MovieAdapter

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = requireArguments().getParcelable("movie")!!

        fetchDetail()
        collectDetail()
    }

    private fun fetchDetail(){
        viewModel.getDetails(movie.id.toInt())
        viewModel.getSimilar(movie.id.toInt())
    }
    private fun collectDetail() {
        viewLifecycleOwner.lifecycleScope.launch{

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){

                launch {

                    viewModel.detailData.collectLatest { data ->
                        if (data != null){
                            viewBind(data)
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.similarData.collectLatest { data ->
                        if (data != null) {
                            similarMovieAdapter = MovieAdapter(
                                requireContext(),
                                data.movies,
                                this@DetailFragment
                            )

                            similarMovieRecyclerView.adapter = similarMovieAdapter
                        }
                    }
                }
            }

        }

    }

    override fun onItemClicked(movie : MovieResultItem){
        viewModel.getDetails(movie.id.toInt())
        detailScrollView.smoothScrollTo(0,0)}

    private fun viewBind(data: MovieDetail) {

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










