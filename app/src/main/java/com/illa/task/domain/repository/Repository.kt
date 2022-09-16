package com.illa.task.domain.repository

import androidx.lifecycle.LiveData
import com.illa.task.common.Resource
import com.illa.task.domain.model.all_movies.AllMoviesResponse

interface Repository {
    fun getMovies(movie: String, apiKey: String, page: Int): LiveData<Resource<AllMoviesResponse>>
}