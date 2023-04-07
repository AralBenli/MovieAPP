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
import com.aralb.movieshowapp.databinding.FragmentDetailBinding
import com.aralb.movieshowapp.models.response.MovieDetail
import com.aralb.movieshowapp.ui.MainActivity
import com.aralb.movieshowapp.ui.viewModels.DetailViewModel
import com.aralb.movieshowapp.util.Constants.imageBase
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding : FragmentDetailBinding
    private var similarMovieAdapter = MovieAdapter()
    private val viewModel by viewModels<DetailViewModel>()
    private var getId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).backNavigation(true)
        (requireActivity() as MainActivity).search(false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getId = requireArguments().getInt("movieId")
        navigation()
        fetchDetail()
        collectDetail()
    }

    private fun fetchDetail(){
        viewModel.getDetails(getId)
        viewModel.getSimilar(getId)
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
                            binding.similarMovieRecyclerView.adapter = similarMovieAdapter
                            similarMovieAdapter.addMovieList(data.movies)
                        }
                    }
                }
            }
        }
    }
    private fun viewBind(data: MovieDetail) {
        with(binding) {
            if (!data.poster_path.isNullOrEmpty()) {
                Glide.with(binding.root)
                    .load(imageBase + data.poster_path)
                    .apply(RequestOptions().override(1080, 720))
                    .into(detailImageView)
            }else{
                detailImageView.setImageResource(R.drawable.default_poster_path)
            }
            detailOverview.text = data.overview
            detailTitleTextView.text = data.title
            detailBudgetTextView.text = data.budget.toString()
            detailOriginalLanguage.text = data.original_language
            detailOriginalTitle.text = data.original_title
            detailPopularity.text = data.popularity.toString()
            detailRatingbar.rating = (data.vote_average.toFloat()) / 2
            viewModel.getSimilar(getId)
        }
    }

    private fun navigation(){
        similarMovieAdapter.clickMovie = {
            viewModel.getDetails(it.id.toInt())
            binding.detailScrollView.smoothScrollTo(0,0)
        }
    }
}










