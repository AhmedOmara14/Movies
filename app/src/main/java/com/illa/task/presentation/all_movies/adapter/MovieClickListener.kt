package com.illa.task.presentation.all_movies.adapter

import com.illa.task.domain.model.all_movies.Movie

interface MovieClickListener {
    fun addFavorite(movie: Movie)
    fun deleteFavoriteId(movie: String)
}