package com.example.bitfit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var foods: MutableList<Food>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addFoodButton = findViewById<Button>(R.id.addFoodButton)

        foods = ListFetcher.GetFoodList()

        val listRv = findViewById<RecyclerView>(R.id.listRv)
        val adapter =ItemAdapter(foods)
        listRv.adapter = adapter
        listRv.layoutManager = LinearLayoutManager(this)

        val startForResult:ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK)
            {
                val data = result.data
                if (data != null)
                {
                    val foodName = data.extras!!.getString("food name")
                    val calorieCount = data.extras!!.getString("calorie count")

                    var newFood = Food(foodName.toString(),calorieCount.toString())
                    foods.add(newFood)

                    lifecycleScope.launch(IO)
                    {
                        (application as FoodApplication).db.foodDao().deleteAll()
                        (application as FoodApplication).db.foodDao().insertAll(foods.map {
                            FoodEntity(
                                foodName = it.name,
                                calorieCount = it.calories
                            )
                        })
                    }
                    adapter.notifyDataSetChanged()
                }

            }
        }

        addFoodButton.setOnClickListener(){
            val intent = Intent(this,AddFoodActivity::class.java)
            startForResult.launch(intent)
        }
    }
}