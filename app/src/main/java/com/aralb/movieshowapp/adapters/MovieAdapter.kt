package com.aralb.movieshowapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.databinding.MovieRowItemBinding
import com.aralb.movieshowapp.models.response.MovieResultItem
import com.aralb.movieshowapp.util.Constants.imageBase
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movieList: ArrayList<MovieResultItem> = arrayListOf()
    var clickMovie: ((item: MovieResultItem) -> Unit)? = null

   inner class MovieViewHolder(private val binding: MovieRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieList: MovieResultItem) {
            with(binding) {
                titleTextView.text = movieList.title
                releaseDateTextView.text = movieList.release_date
                latestVoteAverage.rating = (movieList.vote_average.toFloat()) / 2

                if (!movieList.poster_path.isNullOrEmpty()) {
                    Glide.with(binding.root)
                        .load(imageBase + movieList.poster_path)
                        .apply(RequestOptions().override(1080, 720))
                        .into(binding.movieImageView)
                }else{
                       movieImageView.setImageResource(R.drawable.default_poster_path)
                }
            }
            itemView.setOnClickListener {
                clickMovie?.invoke(movieList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movieList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount() = movieList.size


    fun addMovieList(list: List<MovieResultItem>) {
        movieList.clear()
        movieList.addAll(list)
        notifyDataSetChanged()
    }

}