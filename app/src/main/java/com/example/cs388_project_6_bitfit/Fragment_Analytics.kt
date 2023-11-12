package com.example.cs388_project_6_bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView


class Fragment_Analytics : Fragment() {

    private lateinit var valueCalories: TextView
    private lateinit var valueTargetCalories: TextView
    private lateinit var valueProgress: TextView
    private lateinit var caloriesProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_analytics, container, false)

        valueCalories = view.findViewById(R.id.valueCaloriesTextView)
        valueTargetCalories = view.findViewById(R.id.valueTargetCaloriesTextView)
        valueProgress = view.findViewById(R.id.valueProgressTextView)
        caloriesProgressBar = view.findViewById(R.id.caloriesProgressBar)

        valueCalories.text = "500"
        valueTargetCalories.text = "2000"
        valueProgress.text = "Your progress | 25% of target"
        caloriesProgressBar.progress = 25

        return view
    }

}