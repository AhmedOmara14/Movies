package com.illa.task.data.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.illa.task.common.Resource
import com.illa.task.data.remote.Api
import com.illa.task.domain.model.all_movies.AllMoviesResponse
import com.illa.task.domain.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: Api) :
    Repository {

    override fun getMovies(
        movie: String,
        apiKey: String,
        page: Int
    ): LiveData<Resource<AllMoviesResponse>> {
        val movieLiveData = MutableLiveData<Resource<AllMoviesResponse>>()
        movieLiveData.value = Resource.loading()
        api.getMovies(movie, apiKey, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Response<AllMoviesResponse>>() {
                override fun onSuccess(response: Response<AllMoviesResponse>) {
                    movieLiveData.value = Resource.success(response.body())
                }

                override fun onError(e: Throwable) {
                    movieLiveData.value = Resource.error(e.message.toString())
                }
            }
            )
        return movieLiveData
    }


}