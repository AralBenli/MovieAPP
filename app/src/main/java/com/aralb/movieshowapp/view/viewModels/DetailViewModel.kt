package com.aralb.movieshowapp.view.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aralb.movieshowapp.dataReceiverInterface.DetailDataListener
import com.aralb.movieshowapp.models.movieDetail.MovieDetail
import com.aralb.movieshowapp.models.response.MovieResponse
import com.aralb.movieshowapp.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(
    private var  repository : DetailRepository,
): ViewModel() , DetailDataListener {

    val detailModel = MutableLiveData<MovieDetail>()
    val detailLoadingError = MutableLiveData<String>()

    val similarModel = MutableLiveData<MovieResponse>()
    val similarLoadingError = MutableLiveData<String>()

init {
    repository.listenerDetail = this
}

    fun getDetails (id:Int){
    repository.getDetails(id)
}
    fun getSimilar (id: Int) {
    repository.getSimilar(id)
}

    override fun onDetailDataReceived(data: MovieDetail) {
    detailModel.value = data
    }

    override fun onSimilarDataReceived(data: MovieResponse) {
    similarModel.value = data
    }
}

