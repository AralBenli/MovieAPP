package com.aralb.movieshowapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.RecyclerViewClickInterface
import com.aralb.movieshowapp.models.movieData.MovieResultItem
import com.aralb.movieshowapp.util.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.similar_movie_rec_row_item.view.*

class SimilarMovieAdapter (private val context: Context,
                           private val similarMovie : List<MovieResultItem>,
                           private val recyclerViewClickInterface: RecyclerViewClickInterface )
    :RecyclerView.Adapter<SimilarMovieAdapter.SimilarViewHolder>(){

    class SimilarViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {


        var poster_path: ImageView
        var title: TextView
        var vote_average:RatingBar


        init {
            poster_path = itemView.similarMovieImageView
            title = itemView.similarMovieTitle
            vote_average= itemView.similarRating
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {

        val itemView = LayoutInflater.from(context).inflate(R.layout.similar_movie_rec_row_item,parent,false)
        return SimilarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int ) {


        val movie = similarMovie[position]

        holder.title.text = movie.title
        holder.vote_average.rating = (movie.vote_average.toFloat())/2

       Picasso.get()
            .load(Constants.imageBase + movie.poster_path)
            .into(holder.poster_path)

        holder.itemView.similar_root.setOnClickListener {

            recyclerViewClickInterface.onItemClicked(movie.id.toInt())

        }
    }

    override fun getItemCount(): Int {

        return similarMovie.size
    }

}



