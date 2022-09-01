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

class SearchViewModel : ViewModel() {

    val searchModel = MutableLiveData<MovieResponse>()
    val searchLoadingError = MutableLiveData<String>()
    val searchLoading = MutableLiveData<Boolean>()

    fun getSearch(text:String){

        val retrofit = AppModule.retrofitService.getSearch(Constants.api_key,text)
        retrofit.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                val responseBody = response.body()!!
                searchModel.value = responseBody
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {

                Log.d("MainActivity" ,"onFailure: t.message")
                searchLoadingError.value = t.message
            }
        })
    }
}