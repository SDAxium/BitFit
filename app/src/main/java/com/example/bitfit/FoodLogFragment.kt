package com.example.bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FoodLogFragment"

class FoodLogFragment : Fragment() {
    lateinit var foods: MutableList<DisplayFood>
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var itemAdapter:ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_log,container,false)

        foods = ListFetcher.GetFoodList()

        val addFoodButtonFragment = view.findViewById<Button>(R.id.addFoodButton_fragment)
        val layoutManager = LinearLayoutManager(context)

        val listRvFragment = view.findViewById<RecyclerView>(R.id.listRv_fragment)
        listRvFragment.layoutManager = layoutManager
        listRvFragment.setHasFixedSize(true)
        itemAdapter = ItemAdapter(foods)
        listRvFragment.adapter = itemAdapter

        val startForResult: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK)
            {
                val data = result.data
                if (data != null)
                {
                    val foodName = data.extras!!.getString("food name")
                    val calorieCount = data.extras!!.getString("calorie count")

                    var newFood = DisplayFood(foodName.toString(),calorieCount.toString())
                    foods.add(newFood)

                    lifecycleScope.launch(Dispatchers.IO)
                    {
                        (activity?.application as FoodApplication).db.foodDao().deleteAll()
                        (activity?.application as FoodApplication).db.foodDao().insertAll(foods.map {
                            FoodEntity(
                                foodName = it.foodName.toString(),
                                calorieCount = it.calorieCount.toString()
                            )
                        })
                    }
                }

            }
        }

        lifecycleScope.launch {
            (activity?.application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFood(
                        entity.foodName,
                        entity.calorieCount
                    )
                }.also { mappedList ->
                    foods.clear()
                    foods.addAll(mappedList)
                    itemAdapter.notifyDataSetChanged()
                }
            }
        }

        addFoodButtonFragment.setOnClickListener(){
            val intent = Intent(activity,AddFoodActivity::class.java)
            startForResult.launch(intent)
        }
        return view
    }

    companion object {
        fun newInstance(): FoodLogFragment{
         return FoodLogFragment()
        }
    }
}