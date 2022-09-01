package com.aralb.movieshowapp.view.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aralb.movieshowapp.api.AppModule
import com.aralb.movieshowapp.models.movieData.MovieResultItem
import com.aralb.movieshowapp.response.MovieResponse
import com.aralb.movieshowapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel(){

    val listModel = MutableLiveData<List<MovieResultItem>>()
    val listLoadError = MutableLiveData<String>()
    val listLoading = MutableLiveData<Boolean>()

    fun getMovieData(){

    val retrofit = AppModule.retrofitService.getMovieList(Constants.api_key)
        retrofit.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                val responseBody = response.body()!!
                listModel.value = responseBody.movies
            }
            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)

                listLoadError.value = t.message
            }
        }) }
}