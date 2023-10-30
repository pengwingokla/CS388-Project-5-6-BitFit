package com.example.cs388_project_5_bitfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize
        val averageCaloriesTextView = findViewById<TextView>(R.id.valueCaloriesTextView)
        val progressBar = findViewById<ProgressBar>(R.id.caloriesProgressBar)

        // Calculate the average calories
        val averageCalories = calculateAverageCalories()
        averageCaloriesTextView.text = "$averageCalories"

        // Example calculation for the progress
        val dbHelper = SQLiteHelper(this)
        val totalCalories = dbHelper.getTotalCalories()
        val targetCalories = 2000 // Replace with your target calories

        val percentage = (totalCalories * 100) / targetCalories
        progressBar.progress = percentage

    }

    private fun calculateAverageCalories(): Double {
        val dbHelper = SQLiteHelper(this)
        return dbHelper.getAverageCalories()
    }
}