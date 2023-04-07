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
import androidx.navigation.fragment.findNavController
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.MovieAdapter
import com.aralb.movieshowapp.databinding.FragmentMainBinding
import com.aralb.movieshowapp.ui.MainActivity
import com.aralb.movieshowapp.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment(){

    lateinit var binding : FragmentMainBinding
    private val popularMovieAdapter = MovieAdapter()
    private val upComingMovieAdapter = MovieAdapter()
    private val topRatedAdapter = MovieAdapter()


    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).search(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.topRatedRecyclerView.adapter = topRatedAdapter
        binding.upcomingRecyclerView.adapter = upComingMovieAdapter
        binding.popularRecyclerView.adapter = popularMovieAdapter

        navigation()
        fetchMain()
        collectMain()
    }

    private fun fetchMain() {
        viewModel.getUpcoming()
        viewModel.getTopRated()
        viewModel.getPopular()
    }

    private fun collectMain() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.popularData.collectLatest { data ->
                        if (data != null) {
                            popularMovieAdapter.addMovieList(data.movies)
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.upcomingData.collectLatest { data ->
                        if (data != null) {
                            upComingMovieAdapter.addMovieList(data.movies)
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.topRatedData.collectLatest { data ->
                        if (data != null) {
                            topRatedAdapter.addMovieList(data.movies)
                        }
                    }
                }
            }
        }
    }

    private fun navigation() {
        topRatedAdapter.clickMovie = {
            val bundle = Bundle()
            bundle.putInt("movieId" , it.id.toInt())
            findNavController().navigate(R.id.action_MainFragment_to_detailsFragment, bundle)
        }
        popularMovieAdapter.clickMovie = {
            val bundle = Bundle()
            bundle.putInt("movieId" , it.id.toInt())
            findNavController().navigate(R.id.action_MainFragment_to_detailsFragment, bundle)
        }
        upComingMovieAdapter.clickMovie = {
            val bundle = Bundle()
            bundle.putInt("movieId" , it.id.toInt())
            findNavController().navigate(R.id.action_MainFragment_to_detailsFragment, bundle)
        }
    }
}










