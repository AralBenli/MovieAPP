package com.aralb.movieshowapp.adapters

import com.aralb.movieshowapp.models.response.MovieResultItem

interface RecyclerViewClickInterface {
   fun onItemClicked(movie : MovieResultItem)
}
