package com.illa.task.presentation.wishlist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.illa.task.R
import com.illa.task.common.*
import com.illa.task.databinding.FragmentWishlistBinding
import com.illa.task.domain.model.all_movies.Movie
import com.illa.task.presentation.wishlist.adapter.WishListAdapter
import com.illa.task.presentation.wishlist.adapter.WishlistClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment(), WishlistClickListener {
    private lateinit var wishListAdapter: WishListAdapter
    private lateinit var binding: FragmentWishlistBinding
    private val viewModel: WishlistViewModel by viewModels()
    private var wishList: List<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_wishlist,
                container,
                false
            )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMovieAdapter()
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
            badge?.number = movies.size
            setDataInList(wishList as java.util.ArrayList<Movie>)
        }
    }

    private fun initMovieAdapter() {
        wishListAdapter = WishListAdapter(
            this
        )
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvAllMovies.layoutManager = linearLayoutManager
        binding.rvAllMovies.adapter = wishListAdapter

    }

    override fun onResume() {
        super.onResume()
        getWishlist()
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun setDataInList(allMoviesList: java.util.ArrayList<Movie>) {
        if (allMoviesList.isNotEmpty()) {
            binding.rvAllMovies.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
            wishListAdapter.setWishlist(allMoviesList)
            wishListAdapter.notifyDataSetChanged()
        }
        else{
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvAllMovies.visibility = View.GONE
        }
    }
    override fun deleteFavoriteId(movie: String) {
        viewModel.deleteMovie(movie)
        HelperClass.showToast(context, getString(R.string.movie_is_deleted))
        getWishlist()
    }


}