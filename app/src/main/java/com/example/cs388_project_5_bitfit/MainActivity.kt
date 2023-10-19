package com.example.cs388_project_5_bitfit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var foodList: ArrayList<TrackerModel>
    private lateinit var btnAdd: Button
    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: FoodItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqliteHelper = SQLiteHelper(this)
        btnAdd = findViewById(R.id.addButton)

        btnAdd.setOnClickListener {
            // Create an Intent to open the InputScreen
            val intent = Intent(this, InputScreen::class.java)
            startActivity(intent)
        }


        // Set up the RecyclerView and its adapter
        recyclerView = findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val foodList = sqliteHelper.getAllFood()
        adapter = FoodItemAdapter(foodList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

}
