package com.example.cs388_project_6_bitfit

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class Fragment_InputTarget : Fragment() {
    private lateinit var dbHelper: SQLiteHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__input_target, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the database helper
        dbHelper = SQLiteHelper(requireContext())

        // USER INPUT: only allow digits and backspace
        val targetEditText: EditText = view.findViewById(R.id.targetEditText)
        targetEditText.inputType = InputType.TYPE_CLASS_NUMBER
        val inputFilters = arrayOf<InputFilter>(InputFilter { source, _, _, _, _, _ ->
            source.filter { it.isDigit() || it == '-' }
        })
        targetEditText.filters = inputFilters

        // SUBMIT BUTTON: update database and navigate to Fragment_Analytics
        val submitButton: Button = view.findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            val targetCal = targetEditText.text.toString()
            if (targetCal.isNotEmpty()) {
                val calories = targetCal.toInt()
                val newRowId = dbHelper.updateTargetCalories(calories)
                Log.d("Fragment_InputTarget", "Inserted/Updated target calories. Row ID: $newRowId")

                // Check if insertion or update was successful
                if (newRowId > 0) {
                    Toast.makeText(requireContext(), "Target Calories Updated", Toast.LENGTH_SHORT).show()
                    activity?.supportFragmentManager?.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Failed to Update Target Calories", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}