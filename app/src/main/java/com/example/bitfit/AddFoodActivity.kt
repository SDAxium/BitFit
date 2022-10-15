package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddFoodActivity : AppCompatActivity(){
    val FOOD_NAME = "food name"
    val CALORIE_COUNT = "calorie count"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        val foodRecordButton = findViewById<Button>(R.id.foodRecordButton)
        val foodInput = findViewById<EditText>(R.id.foodInput)
        val calorieInput = findViewById<EditText>(R.id.calorieInput)

        foodRecordButton.setOnClickListener{
            var data = Intent()
            data.putExtra(FOOD_NAME,foodInput.text.toString())
            data.putExtra(CALORIE_COUNT,calorieInput.text.toString())
            setResult(RESULT_OK,data)
            finish()
        }
    }
}
