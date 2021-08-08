package com.example.foodbook.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodbook.model.Food
@Dao
interface FoodDAO {
    @Insert
    suspend fun insertAll(vararg food: Food):List<Long>
    //suspend -> coroutine scope
    // vararg -> more than one food and as many foods as we want
    //List<Long> -> long, id 's
    @Query("SELECT*FROM food")
    suspend fun getAllFood():List<Food>

    @Query("SELECT*FROM food WHERE uuid = :foodId")
    suspend fun getFood(foodId:Int):Food

    @Query("DELETE FROM food ")
    suspend fun deleteAllFood()
}