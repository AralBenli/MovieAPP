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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.MovieAdapter
import com.aralb.movieshowapp.adapters.RecyclerViewClickInterface
import com.aralb.movieshowapp.models.response.MovieResultItem
import com.aralb.movieshowapp.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() , RecyclerViewClickInterface {

    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var upComingMovieAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter

    private val viewModel by viewModels<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
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

        viewModel.getPopular()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.popularData.collectLatest { data ->
                        if (data != null) {

                            popularMovieAdapter = MovieAdapter(
                                requireContext(),
                                data.movies,
                                this@MainFragment
                            )

                            popularRecyclerView.adapter = popularMovieAdapter
                        }
                    }
                }

            }
        }


        // UPCOMING MOVIES

        upcomingRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        upcomingRecyclerView.setHasFixedSize(true)

        viewModel.getUpcoming()

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.upcomingData.collectLatest { data ->
                        if (data != null) {
                            upComingMovieAdapter = MovieAdapter(
                                requireContext(),
                                data.movies,
                                this@MainFragment
                            )
                            upcomingRecyclerView.adapter = upComingMovieAdapter
                        }
                    }
                }

        }

}

        // TOP RATED MOVIES

        topRatedRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        topRatedRecyclerView.setHasFixedSize(true)

        viewModel.getTopRated()

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.topRatedData.collectLatest { data ->
                        if (data != null){
                            topRatedAdapter = MovieAdapter(
                                requireContext(),
                                data.movies,
                                this@MainFragment
                            )
                            topRatedRecyclerView.adapter = topRatedAdapter
                        }
                    }
                }

          }
        }


}
    override fun onItemClicked(movie: MovieResultItem) {

        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        findNavController().navigate(R.id.action_MainFragment_to_detailsFragment, bundle)

    }
}






