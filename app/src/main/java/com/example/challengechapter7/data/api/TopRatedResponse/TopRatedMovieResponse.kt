package com.example.challengechapter7.data.api.TopRatedResponse

data class TopRatedMovieResponse(
    val page: Int,
    val results: List<TopRated>,
    val total_pages: Int,
    val total_results: Int
)