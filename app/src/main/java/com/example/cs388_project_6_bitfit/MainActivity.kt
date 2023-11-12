package com.example.cs388_project_6_bitfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load Fragment_Home when app starts
        loadFragment(Fragment_Home())

        // Handle bottom navigation item clicks
        val homeButton: Button = findViewById(R.id.HomeButton)
        val analyticsButton: Button = findViewById(R.id.AnalyticsButton)

        homeButton.setOnClickListener {
            loadFragment(Fragment_Home())
        }

        analyticsButton.setOnClickListener {
            loadFragment(Fragment_Analytics())
        }
    }
    // Method to load a fragment
    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}