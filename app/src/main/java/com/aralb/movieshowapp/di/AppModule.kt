package com.aralb.movieshowapp.di

import com.aralb.movieshowapp.apiService.MovieService
import com.aralb.movieshowapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {

    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()
}

    @Provides
    @Singleton
    fun provideMovieApi(retrofit : Retrofit): MovieService{
    return retrofit.create(MovieService::class.java)
    }

}