package com.aralb.movieshowapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aralb.movieshowapp.models.response.MovieResponse
import com.aralb.movieshowapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
    ) :ViewModel() {

    private val popularResponse : MovieResponse? = null
    private  val topRatedResponse : MovieResponse? = null
    private val upcomingResponse : MovieResponse? = null

    private val _popularData = MutableStateFlow(popularResponse)
    val popularData = _popularData.asStateFlow()

    private val _upcomingData = MutableStateFlow(upcomingResponse)
    val upcomingData = _upcomingData.asStateFlow()

    private val _topRatedData = MutableStateFlow(topRatedResponse)
    val topRatedData = _topRatedData.asStateFlow()

    fun getPopular(){
        viewModelScope.launch {
            repository.getPopular().collect { values ->
                _popularData.value = values
            }
        }
    }
    fun getTopRated(){
        viewModelScope.launch {
            repository.getTopRated().collect{ values ->
                _topRatedData.value = values
            }
        }
    }
    fun getUpcoming(){
        viewModelScope.launch {
            repository.getUpcoming().collect{ values ->
                _upcomingData.value = values
            }
        }
    }

}







