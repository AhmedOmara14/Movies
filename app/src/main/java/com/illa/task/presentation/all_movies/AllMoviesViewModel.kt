package com.illa.task.presentation.all_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.illa.task.common.Resource
import com.illa.task.data.db.MovieDao
import com.illa.task.domain.model.all_movies.AllMoviesResponse
import com.illa.task.domain.model.all_movies.Movie
import com.illa.task.domain.repository.DatabaseRepository
import com.illa.task.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    private val repository: Repository,
    private val repository_DB: DatabaseRepository
) : ViewModel() {

    fun getMovies(movie: String, apiKey: String, page: Int): LiveData<Resource<AllMoviesResponse>> {
        return repository.getMovies(movie, apiKey, page)
    }

    fun insertMovie(movie: Movie) {
        repository_DB.insertMovie(movie)
    }

    fun deleteMovie(movieId: String) {
        repository_DB.deleteMovie(movieId)
    }

    fun getWishlist(): LiveData<List<Movie>> {
        return repository_DB.getWishlist()
    }


}