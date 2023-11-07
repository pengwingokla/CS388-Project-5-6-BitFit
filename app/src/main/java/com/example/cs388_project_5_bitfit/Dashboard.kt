package com.example.cs388_project_5_bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Dashboard : AppCompatActivity() {
    private lateinit var averageCaloriesTV: TextView
    private lateinit var percentageTV: TextView
    private lateinit var targetCaloriesTV: TextView
    private lateinit var btnMainTab: Button
    private lateinit var btnSetCalories: Button
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize
        averageCaloriesTV = findViewById(R.id.valueCaloriesTextView)
        percentageTV = findViewById(R.id.percentageTextView)
        targetCaloriesTV = findViewById(R.id.valueTargetCaloriesTextView)
        progressBar = findViewById(R.id.caloriesProgressBar)
        btnMainTab = findViewById(R.id.Tab1Button)
        btnSetCalories = findViewById(R.id.setCaloriesButton)

        // Calculate the average calories
        val averageCalories = calculateAverageCalories()
        averageCaloriesTV.text = "$averageCalories"

//        // Example calculation for the progress
//        val dbHelper = SQLiteHelper(this)
//        val totalCalories = dbHelper.getTotalCalories()
//        val targetCalories = 2000
//
//        val percentage = (totalCalories * 100) / targetCalories
//        progressBar.progress = percentage
//        percentageTV.text = "Your progress | " + "$percentage" + "% of target"

        // GO TO Main Tab
        btnMainTab.setOnClickListener{
            // Create an Intent to open the Dashboard Screen
            val intentDashboard = Intent(this, MainActivity::class.java)
            startActivity(intentDashboard)
        }

        // GO TO Set Target Calories
        val dbHelper = SQLiteHelper(this)
        btnSetCalories.setOnClickListener{
            // Create an AlertDialog
            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.popup_target_calories, null)
            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()

            val targetCaloriesEditText = dialogView.findViewById<EditText>(R.id.targetCaloriesEditText)
            val saveButton = dialogView.findViewById<Button>(R.id.saveTargetCaloriesButton)

            saveButton.setOnClickListener {
                val targetCaloriesText = targetCaloriesEditText.text.toString()
                if (targetCaloriesText.isNotEmpty()) {
                    val targetCalories = targetCaloriesText.toInt()
                    dbHelper.setTargetCalories(targetCalories)
                    alertDialog.dismiss()
                }
            }

            alertDialog.show()
        }



    }

    private fun calculateAverageCalories(): Double {
        val dbHelper = SQLiteHelper(this)
        return dbHelper.getAverageCalories()
    }
}