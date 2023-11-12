package com.example.cs388_project_6_bitfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load Fragment_Home when app starts
        if (savedInstanceState == null) {
            loadFragment(Fragment_Home.newInstance())
        }
        // Handle bottom navigation item clicks
        val homeButton: Button = findViewById(R.id.HomeButton)
        val analyticsButton: Button = findViewById(R.id.AnalyticsButton)

        homeButton.setOnClickListener {
            loadFragment(Fragment_Home.newInstance())
        }

//        analyticsButton.setOnClickListener {
//            loadFragment(Fragment_Analytics.newInstance())
//        }
    }
    // Method to load a fragment
    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}