package com.aralb.movieshowapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.adapters.ListAdapter
import com.aralb.movieshowapp.models.movieData.MovieResultItem
import com.aralb.movieshowapp.view.viewModels.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*



class ListFragment : Fragment() {

    private lateinit var gridLayoutManager : GridLayoutManager
    lateinit var listAdapter : ListAdapter
    lateinit var viewModelList : ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        viewModelList = ViewModelProvider(this)[ListViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//       (activity as AppCompatActivity).supportActionBar?.title = "Free Movies"

        gridLayoutManager = GridLayoutManager(requireContext(),2)
        listRecyclerView.layoutManager= gridLayoutManager
        listRecyclerView.setHasFixedSize(true)

       viewModelList.getMovieData()

        val dataObserver = Observer<List<MovieResultItem>> {  data ->

            listAdapter = ListAdapter(
                requireContext() ,
                data ,
                findNavController()
            )

            listRecyclerView.adapter = listAdapter
        }

        val errorObserver = Observer<String> {  error ->
            Log.e("hata",error)
        }

        viewModelList.listModel.observe(requireActivity(),dataObserver)
        viewModelList.listLoadError.observe(requireActivity(),errorObserver)


        view.listScopeImageView.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_searchFragment)

        }
    }
}
