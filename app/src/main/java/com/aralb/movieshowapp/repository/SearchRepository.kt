package com.aralb.movieshowapp.repository

import com.aralb.movieshowapp.apiService.MovieService
import com.aralb.movieshowapp.models.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SearchRepository @Inject constructor(private val movieService: MovieService) {

    suspend fun getSearch(text: String?): Flow<MovieResponse> = flow {
        emit(movieService.getSearch(query = text))
    }.flowOn(Dispatchers.IO)
}