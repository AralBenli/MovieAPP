package com.aralb.movieshowapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.models.response.MovieResultItem
import com.aralb.movieshowapp.util.Constants.imageBase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_row_item.view.*

class MovieAdapter(private val context: Context,
                   private val movies: List<MovieResultItem>,
                   private val recyclerViewClickInterface: RecyclerViewClickInterface

) :RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    class MovieViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

        var poster_path : ImageView
        var title: TextView
        var release_date : TextView
        var vote_average : RatingBar
        private val movie_root: ConstraintLayout
        init {
            poster_path = itemView.movieImageView
            title = itemView.titleTextView
            release_date = itemView.releaseDateTextView
            vote_average = itemView.latestVoteAverage
            movie_root = itemView.movie_root
        } }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.movie_row_item,parent,false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie : MovieResultItem = movies[position]

            holder.title.text= movie.title
            holder.release_date.text = movie.release_date
            holder.vote_average.rating = (movie.vote_average.toFloat())/2

        Picasso.get()
                .load(imageBase + movie.poster_path)
                .into(holder.poster_path)


        holder.itemView.movie_root.setOnClickListener {
            recyclerViewClickInterface.onItemClicked(movie)
        }
    }
    override fun getItemCount(): Int {
        return movies.size
    }
}