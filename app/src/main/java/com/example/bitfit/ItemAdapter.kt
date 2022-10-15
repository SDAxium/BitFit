package com.example.bitfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter (private val foods:List<DisplayFood>): RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val nameTextView: TextView
        val calorieTextView: TextView

        init {
            nameTextView = itemView.findViewById(R.id.foodName)
            calorieTextView = itemView.findViewById(R.id.foodCalories)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val itemView = inflater.inflate(R.layout.food_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]

        holder.nameTextView.text = food.foodName
        holder.calorieTextView.text = food.calorieCount
    }

    override fun getItemCount(): Int {
        return foods.size
    }
}