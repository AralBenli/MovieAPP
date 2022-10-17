package com.aralb.movieshowapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aralb.movieshowapp.models.response.MovieResponse
import com.aralb.movieshowapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
) : ViewModel() {

    private val searchResponse: MovieResponse? = null


    private val _searchData = MutableStateFlow(searchResponse)
    val searchData = _searchData.asStateFlow()

    fun getSearch(text: String) {
        viewModelScope.launch {
            repository.getSearch(text).collect { values ->
                _searchData.value = values
            }
        }
    }
}




