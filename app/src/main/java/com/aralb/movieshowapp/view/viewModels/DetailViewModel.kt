package com.aralb.movieshowapp.view.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aralb.movieshowapp.api.Service
import com.aralb.movieshowapp.models.movieDetail.MovieDetail
import com.aralb.movieshowapp.response.MovieResponse
import com.aralb.movieshowapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    val detailModel = MutableLiveData<MovieDetail>()
    val detailLoadingError = MutableLiveData<String>()

    val similarModel = MutableLiveData<MovieResponse>()
    val similarLoadingError = MutableLiveData<String>()

    fun getDetails(id: Int) {
        val retrofit = Service.retrofitService.getDetails(id, Constants.api_key)
        retrofit.enqueue(object : Callback<MovieDetail?> {

            override fun onResponse(call: Call<MovieDetail?>, response: Response<MovieDetail?>) {

                val responseBody = response.body()!!

                detailModel.value = responseBody
            }

            override fun onFailure(call: Call<MovieDetail?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)

                detailLoadingError.value = t.message
            }
        })
    }
    fun getSimilar(id: Int) {

        val retrofit = Service.retrofitService.getSimilar(id, Constants.api_key)
        retrofit.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                val responseBody = response.body()!!
                similarModel.value = responseBody

            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
                similarLoadingError.value = t.message
            }
        })
    }




}

