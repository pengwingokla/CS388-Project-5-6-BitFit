package com.example.cs388_project_6_bitfit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Fragment_Home : Fragment() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ADD BUTTON click navigation to Fragment_Input
        val addButton: Button = view.findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val fragmentInput = Fragment_Input()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragmentInput)
                .addToBackStack(null)
                .commit()
        }
        // REMOVE ALL BUTTON deletes all food entries
        val removeButton: Button = view.findViewById(R.id.removeallButton)
        removeButton.setOnClickListener{
            dbHelper.deleteAllFood()
            updateRecyclerView()
        }
        // Initialize the SQLiteHelper, RecyclerView and its adapter then update the recyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.inputRecyclerView)
        dbHelper = SQLiteHelper(requireContext())
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewAdapter
        updateRecyclerView()
    }

    // Function to update RecyclerView with data from the database
    private fun updateRecyclerView() {
        val foodEntries = dbHelper.getAllFoodEntries()
        recyclerViewAdapter.setFoodEntries(foodEntries)
    }

    companion object {
        fun newInstance(): Fragment_Home {
            return Fragment_Home()
        }
    }
}
