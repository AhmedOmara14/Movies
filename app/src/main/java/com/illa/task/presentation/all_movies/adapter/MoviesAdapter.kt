package com.illa.task.presentation.all_movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.illa.task.databinding.ItemMovieBinding
import com.illa.task.domain.model.all_movies.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter(private var listener: MovieClickListener) :
    RecyclerView.Adapter<MoviesAdapter.viewHolder>() {
    private var allMovies: List<Movie>? = null
    private var wishList: List<Movie>? = null

    fun setAllMovies(allMovies: List<Movie>?) {
        this.allMovies = allMovies
    }

    fun setWishlist(allMovies: List<Movie>?) {
        this.wishList = allMovies
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
        holder.bind(allMovies!![position], wishList!!)
        holder.binding.ivFavoriteNotActive.setOnClickListener {
            listener.addFavorite(movie = allMovies!![position])
            holder.binding.ivFavoriteActive.visibility=View.VISIBLE
            holder.binding.ivFavoriteNotActive.visibility=View.INVISIBLE
        }
        holder.binding.ivFavoriteActive.setOnClickListener {
            listener.deleteFavoriteId(movie = allMovies!![position].imdbID)
            holder.binding.ivFavoriteNotActive.visibility=View.VISIBLE
            holder.binding.ivFavoriteActive.visibility=View.INVISIBLE
        }


    }

    override fun getItemCount(): Int {
        return if (allMovies != null) allMovies!!.size else 0
    }
    class viewHolder(var binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?,wishList:List<Movie>) {
            for (movie_ in wishList){
                if (movie?.imdbID==movie_.imdbID){
                    binding.ivFavoriteNotActive.visibility=View.INVISIBLE
                    binding.ivFavoriteActive.visibility=View.VISIBLE
                    break
                }
                else{
                    binding.ivFavoriteNotActive.visibility=View.VISIBLE
                    binding.ivFavoriteActive.visibility=View.INVISIBLE
                }
            }

            Picasso.get().load(movie?.Poster).into(binding.ivMovie)
            binding.movie=movie
        }
        init {
            this.binding = binding
        }
    }

}
