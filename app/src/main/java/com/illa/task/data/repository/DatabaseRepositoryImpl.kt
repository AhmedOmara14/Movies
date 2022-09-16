package com.illa.task.data.repository

import androidx.lifecycle.LiveData
import com.illa.task.data.db.MovieDB
import com.illa.task.domain.model.all_movies.Movie
import com.illa.task.domain.repository.DatabaseRepository
import javax.inject.Inject


class DatabaseRepositoryImpl @Inject constructor(private val db: MovieDB):
    DatabaseRepository {


    override fun insertMovie(movie: Movie) {
        db.movieDao.insert(movie)
    }

    override fun deleteMovie(movieId: String) {
        db.movieDao.deleteMovie(movieId)
    }

    override fun getWishlist(): LiveData<List<Movie>> {
        return db.movieDao.getMovies()
    }

}