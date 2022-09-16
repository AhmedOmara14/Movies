package com.illa.task.domain.repository

import androidx.lifecycle.LiveData
import com.illa.task.domain.model.all_movies.Movie

interface DatabaseRepository {
    fun insertMovie(movie: Movie)
    fun deleteMovie(movieId: String)
    fun getWishlist(): LiveData<List<Movie>>

}