package com.aralb.movieshowapp.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.models.movieData.MovieResultItem
import com.aralb.movieshowapp.util.Constants.imageBase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_row_item.view.*

class ListAdapter(private val context: Context, private val movies: List<MovieResultItem>?, private val navController: NavController)
    :RecyclerView.Adapter<ListAdapter.MovieViewHolder>(){
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
            vote_average = itemView.listRating
            movie_root = itemView.movie_root
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val itemView = LayoutInflater.from(context).inflate(R.layout.movie_row_item,parent,false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movieId = movies!![position]


            holder.title.text=movieId.title
            holder.release_date.text=movieId.release_date
            holder.vote_average.rating = (movieId.vote_average.toFloat())/2

            Picasso.get()
                .load(imageBase + movieId.poster_path)
                .into(holder.poster_path)


        val bundle = Bundle()
        bundle.putParcelable("movie", movieId )

        holder.itemView.movie_root.setOnClickListener {
            navController.navigate(R.id.action_ListFragment_to_detailsFragment , bundle)
        }
    }
    override fun getItemCount(): Int {
        return movies!!.size
    }

}