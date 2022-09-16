package com.illa.task.presentation.wishlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.illa.task.databinding.ItemMovieBinding
import com.illa.task.domain.model.all_movies.Movie
import com.squareup.picasso.Picasso

class WishListAdapter(private var listener: WishlistClickListener) :
    RecyclerView.Adapter<WishListAdapter.viewHolder>() {
    private var allMovies: List<Movie>? = null


    fun setWishlist(allMovies: List<Movie>?) {
        this.allMovies = allMovies
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(allMovies!![position])
        holder.binding.ivFavoriteActive.visibility = View.VISIBLE
        holder.binding.ivFavoriteNotActive.visibility = View.INVISIBLE
        holder.binding.ivFavoriteActive.setOnClickListener {
            listener.deleteFavoriteId(movie = allMovies!![position].imdbID)
            holder.binding.ivFavoriteNotActive.visibility = View.VISIBLE
            holder.binding.ivFavoriteActive.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return if (allMovies != null) allMovies!!.size else 0
    }

    class viewHolder(var binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?) {
            Picasso.get().load(movie?.Poster).into(binding.ivMovie)
            binding.movie = movie
        }

        init {
            this.binding = binding
        }
    }

}
