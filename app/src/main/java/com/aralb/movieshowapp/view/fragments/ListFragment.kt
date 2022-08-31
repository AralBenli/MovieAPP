package com.aralb.movieshowapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.ListAdapter
import com.aralb.movieshowapp.response.MovieResponse
import com.aralb.movieshowapp.api.AppModule
import com.aralb.movieshowapp.util.Constants
import com.aralb.movieshowapp.view.viewModels.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ListFragment : Fragment() {

    private lateinit var gridLayoutManager : GridLayoutManager
    lateinit var listAdapter : ListAdapter
    lateinit var viewModel : ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)


        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//       (activity as AppCompatActivity).supportActionBar?.title = "Free Movies"

        gridLayoutManager = GridLayoutManager(requireContext(),2)
        listRecyclerView.layoutManager= gridLayoutManager
        listRecyclerView.setHasFixedSize(true)



        getMovieData ()

        view.listScopeImageView.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_searchFragment)
        }
    }


    private fun getMovieData(){

        val retrofitData = AppModule.retrofitService.getMovieList(Constants.api_key)

        retrofitData.enqueue(object : Callback<MovieResponse?> {


            override fun onResponse(call: Call<MovieResponse?>, response: Response<MovieResponse?>) {

                val responseBody = response.body()!!
                listAdapter = ListAdapter(requireContext() , responseBody.movies , findNavController())
                listRecyclerView.adapter = listAdapter

            }
            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            } } ) }
}
