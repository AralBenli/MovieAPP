package com.aralb.movieshowapp.apiService

import com.aralb.movieshowapp.models.response.MovieDetail
import com.aralb.movieshowapp.models.response.MovieResponse
import com.aralb.movieshowapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {

    @GET("3/movie/popular")
    suspend fun getPopular(
        @Query("api_key") api_key: String = Constants.api_key
    ): MovieResponse

    @GET("3/movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") api_key: String = Constants.api_key
    ): MovieResponse

    @GET("3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") api_key: String = Constants.api_key
    ): MovieResponse

    @GET("3/movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") api_key: String = Constants.api_key
    ): MovieDetail

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilar(
        @Path("movie_id") id: Int,
        @Query("api_key") api_key: String = Constants.api_key
    ): MovieResponse

    @GET("3/search/movie")
    suspend fun getSearch(
        @Query("api_key") api_key: String = Constants.api_key,
        @Query("query") query: String?
    ): MovieResponse

}
