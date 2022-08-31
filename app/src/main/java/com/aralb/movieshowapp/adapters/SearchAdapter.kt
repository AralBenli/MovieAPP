package com.aralb.movieshowapp.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.models.movieData.MovieResultItem
import com.aralb.movieshowapp.util.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.search_row_item.view.*

class SearchAdapter(private val context: Context , private val search :List<MovieResultItem>
, private val navController: NavController)
    :RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    class SearchViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {

        var poster_path : ImageView
        var title: TextView
        var vote_average : RatingBar
        private val search_root: LinearLayout

        init{
            poster_path = itemView.searchMovieImageView
            title = itemView.searchMovieTitle
            vote_average = itemView.searchRating
            search_root = itemView.search_root
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.search_row_item , parent , false)
        return SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchMovie = search[position]

        holder.title.text=searchMovie.title
        holder.vote_average.rating = (searchMovie.vote_average.toFloat())/2

        Picasso.get()
            .load(Constants.imageBase +searchMovie.poster_path)
            .into(holder.poster_path)


        val bundle = Bundle()
        bundle.putParcelable("movie",searchMovie)

        holder.itemView.search_root.setOnClickListener {
            navController.navigate(R.id.action_searchFragment_to_detailsFragment , bundle)

        }
    }
    override fun getItemCount(): Int {
        return search.size
    }
}