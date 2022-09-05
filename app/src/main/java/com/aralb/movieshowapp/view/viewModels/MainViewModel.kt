package com.aralb.movieshowapp.view.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aralb.movieshowapp.api.Service
import com.aralb.movieshowapp.response.MovieResponse
import com.aralb.movieshowapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    val popularMovieModel = MutableLiveData<MovieResponse>()
    val popularMovieLoadingError = MutableLiveData<String>()

    val upcomingMovieModel = MutableLiveData<MovieResponse>()
    val upcomingMovieLoadingError = MutableLiveData<String>()

    val topRatedMovieModel = MutableLiveData<MovieResponse>()
    val topRatedMovieLoadingError = MutableLiveData<String>()

    fun getPopularMovieData(){

        val retrofit = Service.retrofitService.getMovieList(Constants.api_key)

        retrofit.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                val responseBody = response.body()!!
                popularMovieModel.value = responseBody
            }
            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)

                popularMovieLoadingError.value = t.message
            }
        }) }

    fun getUpcomingMovieData(){

        val retrofit = Service.retrofitService.getUpcoming(Constants.api_key)

        retrofit.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                val responseBody = response.body()!!
                upcomingMovieModel.value = responseBody
            }
            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
                upcomingMovieLoadingError.value = t.message
            }
        }) }

    fun getTopRated(){
        val retrofit = Service.retrofitService.getTopRated(Constants.api_key)

        retrofit.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
               val responseBody = response.body()!!
               topRatedMovieModel.value = responseBody
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
                topRatedMovieLoadingError.value = t.message
            }
        })
    }
}