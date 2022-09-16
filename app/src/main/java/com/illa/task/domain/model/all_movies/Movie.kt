package com.illa.task.domain.model.all_movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Wishlist")
data class Movie(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")
    var id: Int = 0,
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
)