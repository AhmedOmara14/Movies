package com.illa.task.presentation.all_movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.illa.task.R
import com.illa.task.common.Constants.TAG_LOADING
import com.illa.task.common.HelperClass
import com.illa.task.common.LoadingDialog
import com.illa.task.common.NetworkConnection
import com.illa.task.common.Status
import com.illa.task.databinding.FragmentAllMoviesBinding
import com.illa.task.domain.model.all_movies.Movie
import com.illa.task.presentation.all_movies.adapter.MovieClickListener
import com.illa.task.presentation.all_movies.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMoviesFragment : Fragment(), MovieClickListener {
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: FragmentAllMoviesBinding
    private val viewModel: AllMoviesViewModel by viewModels()
    private lateinit var networkConnection: NetworkConnection
    private val movie: String = "action"
    private val apiKey: String = "cfbf3f6b"
    private var loadingDialog: LoadingDialog? = null
    private var page = 1
    private var allMoviesList: ArrayList<Movie> = ArrayList()
    private var wishList: List<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_all_movies,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
        initMovieAdapter()
        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                getData()
            }
        }

        getWishlist()
    }

    private fun getWishlist() {
        viewModel.getWishlist().observe(viewLifecycleOwner) { movies ->
            wishList =movies
            val badge =
                activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                    ?.getOrCreateBadge(
                        R.id.navigation_wishlist
                    )
            badge?.isVisible = true
            badge?.badgeTextColor=resources.getColor(R.color.white)
            badge?.number = movies.size

            moviesAdapter.setWishlist(wishList)
        }
    }

    private fun initMovieAdapter() {
        moviesAdapter = MoviesAdapter(
            this
        )
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvAllMovies.layoutManager = linearLayoutManager
        binding.rvAllMovies.adapter = moviesAdapter

    }

    override fun onResume() {
        super.onResume()
        allMoviesList = ArrayList()
        getWishlist()
    }

    private fun init() {
        networkConnection = NetworkConnection(requireContext())
        loadingDialog = LoadingDialog()
        loadingDialog!!.isCancelable = false

    }

    private fun getData() {
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                viewModel.getMovies(movie, apiKey, page)
                    .observe(viewLifecycleOwner) { response ->
                        when (response.status) {
                            Status.LOADING -> {
                                loadingDialog!!.show(
                                    requireActivity().supportFragmentManager,
                                    TAG_LOADING
                                )
                            }
                            Status.SUCCESS -> {
                                loadingDialog?.dismiss()
                                val result = response.data!!
                                result.movies.let { it1 -> allMoviesList.addAll(it1) }
                                setDataInList(allMoviesList)
                                result.movies.clear()
                            }
                            Status.ERROR -> {
                                loadingDialog?.dismiss()
                                HelperClass.showToast(context, response.message)
                            }
                        }
                    }
            } else {
                HelperClass.showToast(context, getString(R.string.no_internet_connection))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataInList(allMoviesList: java.util.ArrayList<Movie>) {
        if (allMoviesList.isNotEmpty()) {
            binding.rvAllMovies.visibility = View.VISIBLE
            moviesAdapter.setAllMovies(allMoviesList)
            moviesAdapter.notifyDataSetChanged()
        }

    }

    override fun addFavorite(movie_: Movie) {
        for (movie in wishList){
            if (movie.imdbID == movie_.imdbID) {
                HelperClass.showToast(context, "Movie is Already in Wishlist")
                return
            }
        }
        viewModel.insertMovie(movie_)
        HelperClass.showToast(context, "Movie is added to Wishlist")
        getWishlist()
    }

    override fun deleteFavoriteId(movie: String) {
        viewModel.deleteMovie(movie)
        HelperClass.showToast(context, getString(R.string.movie_is_deleted))
        getWishlist()
    }


}