package com.aralb.movieshowapp.repository

import com.aralb.movieshowapp.apiService.MovieService
import com.aralb.movieshowapp.models.response.MovieDetail
import com.aralb.movieshowapp.models.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(private val movieService: MovieService) {

    suspend fun getSimilar(id: Int): Flow<MovieResponse> = flow {
        emit(movieService.getSimilar(id))
    }.flowOn(Dispatchers.IO)

    suspend fun getDetails(id: Int): Flow<MovieDetail> = flow {
        emit(movieService.getDetails(id))
    }.flowOn(Dispatchers.IO)
}



