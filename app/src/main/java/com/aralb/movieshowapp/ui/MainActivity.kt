package com.aralb.movieshowapp.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aralb.movieshowapp.R
import com.aralb.movieshowapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        navigation()

    }

    private fun navigation(){
        binding.listScopeImageView.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.action_MainFragment_to_searchFragment)
        }
        binding.backIcon.setOnClickListener {
            navHostFragment.findNavController().popBackStack()
        }
    }

    private fun initViews(){
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }

    fun backNavigation(visibility: Boolean) {
        if (visibility) {
            binding.backIcon.visibility = View.VISIBLE
        } else {
            binding.backIcon.visibility = View.INVISIBLE
        }
    }

    fun search(visibility: Boolean) {
        if (visibility) {
            binding.listScopeImageView.visibility = View.VISIBLE
        } else {
            binding.listScopeImageView.visibility = View.INVISIBLE
        }
    }
}











