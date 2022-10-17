package com.aralb.movieshowapp.repository

import com.aralb.movieshowapp.apiService.MovieService
import com.aralb.movieshowapp.models.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class MainRepository @Inject constructor(private val movieService: MovieService) {

    suspend fun getPopular(): Flow<MovieResponse> = flow {
        emit(movieService.getPopular())
    }.flowOn(Dispatchers.IO)


    suspend fun getTopRated(): Flow<MovieResponse> = flow {
        emit(movieService.getTopRated())
    }.flowOn(Dispatchers.IO)


    suspend fun getUpcoming(): Flow<MovieResponse> = flow {
        emit(movieService.getUpcoming())
    }.flowOn(Dispatchers.IO)

}






