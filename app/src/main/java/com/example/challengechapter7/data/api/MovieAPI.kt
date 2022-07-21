package com.example.challengechapter7.data.api

import com.example.challengechapter7.data.api.DetailsResponse.DetailsMovieResponse
import com.example.challengechapter7.data.api.PlayingResponse.PlayingMovieResponse
import com.example.challengechapter7.data.api.PopularResponse.PopularMovieResponse
import com.example.challengechapter7.data.api.TopRatedResponse.TopRatedMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {
    @GET("movie/now_playing?api_key=0fbaf8c27d542bc99bfc67fb877e3906")
    suspend fun getMoviePlaying(): Response<PlayingMovieResponse>

    @GET("movie/popular?api_key=0fbaf8c27d542bc99bfc67fb877e3906")
    suspend fun getMoviePopular(): Response<PopularMovieResponse>

    @GET("movie/top_rated?api_key=0fbaf8c27d542bc99bfc67fb877e3906")
    suspend fun getMovieTopRated(): Response<TopRatedMovieResponse>

    @GET("movie/{movie_id}?api_key=0fbaf8c27d542bc99bfc67fb877e3906")
    suspend fun getMovieDetails(@Path("movie_id") tmdbID: Int): Response<DetailsMovieResponse>
}