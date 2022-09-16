package com.illa.task.domain.model.all_movies

import com.google.gson.annotations.SerializedName

data class AllMoviesResponse(
    val Response: String,
    @SerializedName("Search")
    val movies: ArrayList<Movie>,
    val totalResults: String
)