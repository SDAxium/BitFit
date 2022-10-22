package com.example.bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    lateinit var foods: MutableList<DisplayFood>
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var itemAdapter:ItemAdapter
    var currentAvg = 0
    var currentMin = 0
    var currentMax = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard,container,false)

        val avgCalories = view.findViewById<TextView>(R.id.avgCalories)
        val minCalories = view.findViewById<TextView>(R.id.minCalories)
        val maxCalories = view.findViewById<TextView>(R.id.maxCalories)

        val avgCaloriesText = view.findViewById<TextView>(R.id.avgCaloriesText)
        val minCaloriesText = view.findViewById<TextView>(R.id.minCaloriesText)
        val maxCaloriesText = view.findViewById<TextView>(R.id.maxCaloriesText)

        foods = ListFetcher.GetFoodList()

        if(foods.size > 0)
        {
            calculateNewValues()
            avgCalories.text = currentAvg.toString()
            minCalories.text = currentMin.toString()
            maxCalories.text = currentMax.toString()
        }
        else
        {
            var textViews = listOf<TextView>(avgCalories,avgCaloriesText,minCalories,minCaloriesText,maxCalories,maxCaloriesText)
            for(i in textViews.indices)
            {
                textViews[i].visibility = View.INVISIBLE
            }
        }


        return view
    }

    private fun calculateNewValues()
    {
        currentAvg = 0
        currentMin = foods[0].calorieCount!!.toInt()
        currentMax = foods[0].calorieCount!!.toInt()

        Log.v("DEBUGGGG", currentAvg.toString())
        for (i in foods.indices)
        {
            Log.v("DEBUGGGG", "foods size: ${foods.size}")
            if (foods[i].calorieCount.toString().toInt() < currentMin)
            {
                Log.v("DEBUGGGG", "NEW MIN")
                currentMin = foods[i].calorieCount.toString().toInt()
            }
            if (foods[i].calorieCount!!.toInt() > currentMax)
            {
                Log.v("DEBUGGGG", "NEW MAX")
                currentMax = foods[i].calorieCount.toString().toInt()
            }

            currentAvg += foods[i].calorieCount.toString().toInt()
            Log.v("DEBUGGGG", currentAvg.toString())
        }

        currentAvg /= foods.size
    }
}