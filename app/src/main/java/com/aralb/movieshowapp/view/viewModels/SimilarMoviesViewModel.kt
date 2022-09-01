package com.aralb.movieshowapp.view.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aralb.movieshowapp.api.AppModule
import com.aralb.movieshowapp.response.MovieResponse
import com.aralb.movieshowapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimilarMoviesViewModel : ViewModel() {


    val similarModel = MutableLiveData<MovieResponse>()
    val similarLoadingError = MutableLiveData<String>()
    val similarLoading = MutableLiveData<Boolean>()

  fun getSimilar(id: Int){

      val retrofit = AppModule.retrofitService.getSimilar(id,Constants.api_key)
      retrofit.enqueue(object : Callback<MovieResponse?> {
          override fun onResponse(call: Call<MovieResponse?>, response: Response<MovieResponse?>) {
              val responseBody = response.body()!!
              similarModel.value=responseBody

          }
          override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
              Log.d("MainActivity", "onFailure: " + t.message)
              similarLoadingError.value = t.message
          }
      })
  }
}