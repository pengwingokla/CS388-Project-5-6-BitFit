package com.example.cs388_project_6_bitfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var foodEntries: List<FoodItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodEntry = foodEntries[position]
        holder.bind(foodEntry)
    }

    override fun getItemCount(): Int {
        return foodEntries.size
    }

    fun setFoodEntries(entries: List<FoodItem>) {
        foodEntries = entries
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foodTextView: TextView = itemView.findViewById(R.id.foodTextView)
        private val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)

        fun bind(foodEntry: FoodItem) {
            foodTextView.text = foodEntry.foodName
            caloriesTextView.text = foodEntry.calories.toString()
        }
    }
}