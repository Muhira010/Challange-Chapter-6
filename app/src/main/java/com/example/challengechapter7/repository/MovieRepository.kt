package com.example.challengechapter7.repository

import com.example.challengechapter7.data.api.DetailsResponse.DetailsMovieResponse
import com.example.challengechapter7.data.api.MovieAPI
import com.example.challengechapter7.data.api.PlayingResponse.PlayingMovieResponse
import com.example.challengechapter7.data.api.PopularResponse.PopularMovieResponse
import com.example.challengechapter7.data.api.TopRatedResponse.TopRatedMovieResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieAPI
) {
    suspend fun getMoviePlaying(): Response<PlayingMovieResponse> {
        return api.getMoviePlaying()
    }

    suspend fun getMoviePopular(): Response<PopularMovieResponse> {
        return api.getMoviePopular()
    }

    suspend fun getMovieTopRated(): Response<TopRatedMovieResponse> {
        return api.getMovieTopRated()
    }

    suspend fun getMovieDetails(tmdbID: Int): Response<DetailsMovieResponse> {
        return api.getMovieDetails(tmdbID = tmdbID)
    }
}