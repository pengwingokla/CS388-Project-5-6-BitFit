package com.example.cs388_project_5_bitfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodItemAdapter (private val foodList: ArrayList<TrackerModel>) :
    RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    // Container to hold references to the views within an item layout
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var foodList: ArrayList<TrackerModel> = ArrayList()
        val foodTextView = itemView.findViewById<TextView>(R.id.foodTextView)
        val caloriesTextView = itemView.findViewById<TextView>(R.id.caloriesTextView)
    }
    // For recyclerView to create a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    // For RV to populate the views within a ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tracker = foodList[position]
        holder.foodTextView.text = tracker.food
        holder.caloriesTextView.text = tracker.calories.toString()
    }

    override fun getItemCount() = foodList.size
    fun clearData() {
        foodList.clear()
        notifyDataSetChanged()
    }

    // Add this method to update the dataset
    fun updateData(newData: ArrayList<TrackerModel>) {
        foodList.clear()
        foodList.addAll(newData)
        notifyDataSetChanged()
    }
}
