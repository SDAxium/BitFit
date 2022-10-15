package com.example.bitfit

class ListFetcher {
    companion object {
        var foodList: MutableList<DisplayFood> = ArrayList()

        fun GetFoodList(): MutableList<DisplayFood> {
            return foodList
        }

        fun AddToFoodList(food:DisplayFood):MutableList<DisplayFood>{
            foodList.add(food)
            return foodList
        }
    }
}