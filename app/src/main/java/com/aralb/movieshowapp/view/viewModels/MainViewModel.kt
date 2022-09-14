package com.aralb.movieshowapp.view.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aralb.movieshowapp.dataReceiverInterface.MainDataListener
import com.aralb.movieshowapp.models.response.MovieResponse
import com.aralb.movieshowapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
    ) :ViewModel() , MainDataListener {

    val popularMovieModel = MutableLiveData<MovieResponse>()
    val popularMovieLoadingError = MutableLiveData<String>()

    val upcomingMovieModel = MutableLiveData<MovieResponse>()
    val upcomingMovieLoadingError = MutableLiveData<String>()

    val topRatedMovieModel = MutableLiveData<MovieResponse>()
    val topRatedMovieLoadingError = MutableLiveData<String>()

    init {

        repository.listenerMain = this

    }


    fun getPopular(){
        repository.getPopular()
    }

    fun getTopRated(){
        repository.getTopRated()
    }

    fun getUpcoming(){
        repository.getUpcoming()
    }


    override fun onPopularDataReceived(data: MovieResponse) {
        popularMovieModel.value = data
    }

    override fun onTopRatedDataReceived(data: MovieResponse) {
    topRatedMovieModel.value = data
    }

    override fun onUpcomingDataReceived(data: MovieResponse) {
        upcomingMovieModel.value = data
    }


}