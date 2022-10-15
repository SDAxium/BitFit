package com.example.bitfit

class ListFetcher {
    companion object {
        var foodList: MutableList<Food> = ArrayList()

        fun GetFoodList(): MutableList<Food> {
            return foodList
        }

        fun AddToFoodList(food:Food):MutableList<Food>{
            foodList.add(food)
            return foodList
        }
    }
}