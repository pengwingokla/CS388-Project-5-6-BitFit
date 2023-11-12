package com.example.cs388_project_6_bitfit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class Fragment_Input : Fragment() {

    private lateinit var dbHelper: SQLiteHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the database helper
        dbHelper = SQLiteHelper(requireContext())

        // Handle the "Add" button click
        val foodEditText: EditText = view.findViewById(R.id.foodEditText)
        val caloriesEditText: EditText = view.findViewById(R.id.caloriesEditText)
        val submitButton: Button = view.findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            val foodName = foodEditText.text.toString()
            val caloriesText = caloriesEditText.text.toString()

            if (foodName.isNotEmpty() && caloriesText.isNotEmpty()) {
                val calories = caloriesText.toInt()

                // Insert data into the database
                val newRowId = dbHelper.insertData(foodName, calories)

                // Notify Fragment_Home to update the RecyclerView adapter
                if (newRowId != -1L && activity is MainActivity) {
                    val mainActivity = activity as MainActivity
                    mainActivity.loadFragment(Fragment_Home.newInstance())
                }
            }
        }
    }
}