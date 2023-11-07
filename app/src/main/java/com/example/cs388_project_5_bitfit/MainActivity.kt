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
    private lateinit var btnRemoveAll: Button
    private lateinit var btnDashboardTab: Button
    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: FoodItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqliteHelper = SQLiteHelper(this)
        btnAdd = findViewById(R.id.addButton)
        btnDashboardTab = findViewById(R.id.Tab2Button)
        btnRemoveAll = findViewById(R.id.removeallButton)

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

        // GO TO Dashboard Tab
        btnDashboardTab.setOnClickListener{
            // Create an Intent to open the Dashboard Screen
            val intentDashboard = Intent(this, Dashboard::class.java)
            startActivity(intentDashboard)
        }
        // Remove All
        btnRemoveAll.setOnClickListener {
            val deletedRows = sqliteHelper.deleteAllFood()
            if (deletedRows > 0) {
                Toast.makeText(this, "All data removed from the database.", Toast.LENGTH_SHORT).show()
                // Refresh the RecyclerView to reflect the changes
                adapter?.clearData()
            } else {
                Toast.makeText(this, "No data to remove.", Toast.LENGTH_SHORT).show()
            }

        }

    }

}
