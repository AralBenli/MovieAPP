package com.aralb.movieshowapp.view.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aralb.movieshowapp.dataReceiverInterface.SearchDataListener
import com.aralb.movieshowapp.models.response.MovieResponse
import com.aralb.movieshowapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
) : ViewModel(), SearchDataListener {

    val searchModel = MutableLiveData<MovieResponse>()
    val searchLoadingError = MutableLiveData<String>()

    init {
        repository.listenerSearch = this
    }

    fun getSearch(text: String){
        repository.getSearch(text)
    }


    override fun onSearchDataReceived(data: MovieResponse) {
    searchModel.value = data
    }


}