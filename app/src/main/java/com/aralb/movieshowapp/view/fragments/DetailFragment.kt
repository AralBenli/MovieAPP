package com.aralb.movieshowapp.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.RecyclerViewClickInterface
import com.aralb.movieshowapp.adapters.SimilarMovieAdapter
import com.aralb.movieshowapp.models.movieData.MovieResultItem
import com.aralb.movieshowapp.models.movieDetail.MovieDetail
import com.aralb.movieshowapp.response.MovieResponse
import com.aralb.movieshowapp.api.AppModule
import com.aralb.movieshowapp.util.Constants
import com.aralb.movieshowapp.util.Constants.imageBase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFragment : Fragment() , RecyclerViewClickInterface{
    private lateinit var movie : MovieResultItem
    private lateinit var view : View
    private lateinit var similarMovieAdapter: SimilarMovieAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail, container, false)
        return view

    }

    override fun getView(): View? {
        return super.getView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (activity as AppCompatActivity).supportActionBar?.title = "Movie Details"


        movie = requireArguments().getParcelable("movie")!!


        getDetails(movie.id.toInt())

       //Similar Movie Adapter recommendation under Movie Detail
        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL , false)
        similarMovieRecyclerView.layoutManager= linearLayoutManager
        similarMovieRecyclerView.setHasFixedSize(true)

    }

    override fun onItemClicked(id :Int) {
        getDetails(id)

    }



    private fun getDetails(id: Int){

        val retrofit = AppModule.retrofitService.getDetails(id, Constants.api_key)
        retrofit.enqueue(object : Callback<MovieDetail?> {
            override fun onResponse(
                call: Call<MovieDetail?>, response: Response<MovieDetail?>) {

                val responseBody = response.body()!!

                Picasso.get()
                    .load(imageBase + responseBody.poster_path)
                    .into(detailImageView)

                view.detailOverview.text = responseBody.overview
                view.detailTitleTextView.text=responseBody.title
                view.detailBudgetTextView.text= responseBody.budget.toString()
                view.detailOriginalLanguage.text=responseBody.original_language
                view.detailOriginalTitle.text = responseBody.original_title
                view.detailPopularity.text = responseBody.popularity.toString()
                view.detailRatingbar.rating=(responseBody.vote_average.toFloat())/2



                getSimilar()
            }
            override fun onFailure(call: Call<MovieDetail?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            } })
    }

    private fun getSimilar(){

        val retrofitSimilar = AppModule.retrofitService.getSimilar(movie.id.toInt(), Constants.api_key )
        retrofitSimilar.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>) {

                val responseBody = response.body()!!

                similarMovieAdapter =

                   SimilarMovieAdapter(requireContext(),
                   responseBody.movies,this@DetailFragment)


                similarMovieRecyclerView.adapter = similarMovieAdapter

                similarMovieAdapter.notifyDataSetChanged()

            }
            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}





