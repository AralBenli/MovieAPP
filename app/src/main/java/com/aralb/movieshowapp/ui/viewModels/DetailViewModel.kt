package com.aralb.movieshowapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aralb.movieshowapp.models.response.MovieDetail
import com.aralb.movieshowapp.models.response.MovieResponse
import com.aralb.movieshowapp.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor (
    private var  repository : DetailRepository,
): ViewModel() {
    private val detailResponse  : MovieDetail? = null
    private val similarResponse : MovieResponse? = null

    private val _detailData  = MutableStateFlow(detailResponse)
    val detailData = _detailData.asStateFlow()

    private val _similarData = MutableStateFlow(similarResponse)
    val similarData = _similarData.asStateFlow()


    fun getDetails(id:Int){
    viewModelScope.launch {
        repository.getDetails(id).collect { values ->
            _detailData.value = values

        }

    }
}
    fun getSimilar(id:Int){
        viewModelScope.launch {
            repository.getSimilar(id).collect { values ->
                _similarData.value = values
            }
        }
    }
}












