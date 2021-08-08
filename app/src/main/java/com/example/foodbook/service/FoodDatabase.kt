package com.example.foodbook.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodbook.model.Food

@Database(entities = arrayOf(Food::class),version = 1)
abstract class FoodDatabase:RoomDatabase() {
    abstract fun foodDao():FoodDAO
    //Singleton
    companion object{
        @Volatile private var instance: FoodDatabase?=null
        private var lock=Any()
        operator fun invoke(context: Context)=instance?: synchronized(lock){
            instance?:  createDatabase(context).also {
                instance=it
            }
        }
        private fun createDatabase(context: Context)= Room.databaseBuilder(context.applicationContext,FoodDatabase::class.java,"fooddatabase").build()
    }
}