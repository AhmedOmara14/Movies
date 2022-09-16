package com.illa.task.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.illa.task.domain.model.all_movies.Movie

@Dao
interface MovieDao {
    @Insert
    fun insert(movie: Movie)

    @Query("delete from Wishlist where imdbID =:imdbID")
    fun deleteMovie(imdbID: String)


    @Query("SELECT * FROM Wishlist")
    fun getMovies(): LiveData<List<Movie>>


}