package com.example.cs388_project_5_bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InputScreen : AppCompatActivity() {

    private lateinit var btnSubmit: Button
    private lateinit var edFood: EditText
    private lateinit var edCalories: EditText
    private lateinit var sqliteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_screen)

        // Initialize
        edFood = findViewById<EditText>(R.id.foodEditText)
        edCalories = findViewById<EditText>(R.id.caloriesEditText)
        btnSubmit = findViewById<Button>(R.id.submitButton)
        sqliteHelper = SQLiteHelper(this)
        // btnSubmit events
        btnSubmit.setOnClickListener {
            addFood()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addFood() {
        val food = edFood.text.toString()
        val calories = edCalories.text.toString().toInt()

        if (food.isEmpty()) {
            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()
        } else {
            val tracker = TrackerModel(food = food, calories = calories)
            val status = sqliteHelper.insertFood(tracker)
            //Check insert success
            if (status > -1) {
                Toast.makeText(this, "Food added...", Toast.LENGTH_SHORT).show()
                clearEditText()
            } else {
                Toast.makeText(this, "Information not saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun clearEditText() {
        edFood.setText("")
        edCalories.setText("")
        edFood.requestFocus()
    }
}
