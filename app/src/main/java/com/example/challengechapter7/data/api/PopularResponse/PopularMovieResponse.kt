package com.example.challengechapter7.data.api.PopularResponse

data class PopularMovieResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Popular>,
    val total_pages: Int,
    val total_results: Int
)