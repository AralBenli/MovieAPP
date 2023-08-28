package com.aralb.movieshowapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.aralb.movieshowapp.R

class DeepLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deepLinkData = intent.data
        if (deepLinkData != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("navigateToHome", true)
            startActivity(intent)
        }
        finish()
    }
}