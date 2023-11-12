package com.example.cs388_project_6_bitfit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentManager


class Fragment_Analytics : Fragment() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var valueCalories: TextView
    private lateinit var valueTargetCalories: TextView
    private lateinit var valueTotalCalories: TextView
    private lateinit var valueProgress: TextView
    private lateinit var caloriesProgressBar: ProgressBar
    override fun onResume() {
        super.onResume()
        loadTargetCalories()
        loadTotalCalories()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_analytics, container, false)

        valueCalories = view.findViewById(R.id.valueCaloriesTextView)
        valueTargetCalories = view.findViewById(R.id.valueTargetCaloriesTextView)
        valueTotalCalories = view.findViewById(R.id.valueTotalCaloriesTextView)
        valueProgress = view.findViewById(R.id.valueProgressTextView)
        caloriesProgressBar = view.findViewById(R.id.caloriesProgressBar)

        valueCalories.text = "0"
        valueTargetCalories.text = "0"
        valueTotalCalories.text = "0"
        valueProgress.text = "Your progress | 0% of target"
        caloriesProgressBar.progress = 0

        dbHelper = SQLiteHelper(requireContext())
        loadAverageCalories()

        // SET TARGET BUTTON navigates to a popup
        val setTargetButton: Button = view.findViewById(R.id.setTargetButton)
        setTargetButton.setOnClickListener {
            loadFragment(Fragment_InputTarget())
        }

        return view
    }

    private fun loadAverageCalories() {
        val averageCalories = dbHelper.getAverageCalories()
        valueCalories.text = averageCalories.toString()
    }

    private fun loadTargetCalories() {
        val targetCalories = dbHelper.getTargetCalories()
        Log.d("Fragment_Analytics", "Loaded target calories: $targetCalories")
        valueTargetCalories.text = targetCalories.toString()
    }

    private fun loadTotalCalories() {
        val totalCalories = dbHelper.getTotalCalories()
        valueTotalCalories.text = totalCalories.toString()

        val targetCalories = dbHelper.getTargetCalories()

        // Calculate the percentage
        val percentage = if (targetCalories > 0) {
            (totalCalories.toFloat() / targetCalories.toFloat() * 100).toInt()
        } else {
            0
        }

        // Update progress bar and progress text
        caloriesProgressBar.progress = percentage
        valueProgress.text = "Your progress | $percentage% of target"
    }

    companion object {
        fun newInstance(): Fragment_Home {
            return Fragment_Home()
        }
    }
    private fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}