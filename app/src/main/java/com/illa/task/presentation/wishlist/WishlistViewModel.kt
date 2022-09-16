package com.illa.task.presentation.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.illa.task.domain.model.all_movies.Movie
import com.illa.task.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val repository_DB: DatabaseRepository
) : ViewModel() {

    fun deleteMovie(movieId: String) {
        repository_DB.deleteMovie(movieId)
    }

    fun getWishlist(): LiveData<List<Movie>> {
        return repository_DB.getWishlist()
    }


}