package com.example.cs388_project_5_bitfit

import java.util.Random

data class TrackerModel (
    //var id: Int = getAutoId(),
    var food: String = "",
    var calories: Int? = null
){
    companion object{
        fun getAutoId(): Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}