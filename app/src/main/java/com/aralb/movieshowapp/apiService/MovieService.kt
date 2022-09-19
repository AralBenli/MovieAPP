package com.aralb.movieshowapp.apiService

import com.aralb.movieshowapp.models.movieDetail.MovieDetail
import com.aralb.movieshowapp.models.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {

@GET("3/movie/popular")
fun getMovieList(@Query("api_key")api_key:String):Call<MovieResponse>

@GET("3/movie/top_rated")
fun getTopRated(@Query("api_key")api_key: String):Call<MovieResponse>

@GET("3/movie/upcoming")
fun getUpcoming(@Query("api_key")api_key:String):Call<MovieResponse>

@GET("3/movie/{movie_id}")
fun getDetails(@Path ("movie_id")id:Int, @Query("api_key") api_key:String):Call<MovieDetail>

@GET("3/movie/{movie_id}/similar")
fun getSimilar(@Path ("movie_id")id:Int, @Query("api_key") api_key:String):Call<MovieResponse>

@GET("3/search/movie")
fun getSearch(@Query ("api_key")api_key:String, @Query("query")query:String?):Call<MovieResponse>

}
