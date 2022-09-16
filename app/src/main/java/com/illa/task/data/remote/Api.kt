package com.illa.task.data.remote

import com.illa.task.domain.model.all_movies.AllMoviesResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET(".")
    fun getMovies(
        @Query("s") movie: String,
        @Query("apikey") apikey: String,
        @Query("page") page:Int,
    ): Single<Response<AllMoviesResponse>>

}