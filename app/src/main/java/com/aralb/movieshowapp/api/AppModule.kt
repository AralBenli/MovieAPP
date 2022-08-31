package com.aralb.movieshowapp.api

import com.aralb.movieshowapp.apiService.ApiCall
import com.aralb.movieshowapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

private fun retrofit() : Retrofit {


    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()
}
    val retrofitService : ApiCall by lazy {
    retrofit().create(ApiCall::class.java)
    }

}