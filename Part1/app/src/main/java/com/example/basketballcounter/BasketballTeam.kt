package com.example.basketballcounter

import java.util.*

//A simple data class for storing and updating points
data class BasketballTeam (var points: Int){
    val id: UUID = UUID.randomUUID()

    fun increasePoints(amount: Int){
        points += amount;
    }

    fun reset(){
        points = 0;
    }
}