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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var foods: MutableList<DisplayFood>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val foodLogFragment: Fragment = FoodLogFragment()
        val dashboardFragment: Fragment = DashboardFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when(item.itemId)
            {
                R.id.log -> fragment = foodLogFragment
                R.id.dashboard -> fragment = dashboardFragment
            }
            replaceFragment(fragment)
            true
        }
        bottomNavigationView.selectedItemId = R.id.log

        foods = ListFetcher.GetFoodList()
//
//        val listRv = findViewById<RecyclerView>(R.id.listRv)
//        val adapter = ItemAdapter(foods)
//        listRv.adapter = adapter
//        listRv.layoutManager = LinearLayoutManager(this)
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.food_frame_layout, fragment)
        fragmentTransaction.commit()
    }
}