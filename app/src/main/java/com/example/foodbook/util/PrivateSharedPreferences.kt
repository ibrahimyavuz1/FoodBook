package com.example.foodbook.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class PrivateSharedPreferences {
    companion object{
        private val TIME= "time"
        private var sharedPreferences: SharedPreferences?=null
        @Volatile private var instance : PrivateSharedPreferences?=null
        private val lock=Any()
        operator fun invoke(context: Context): PrivateSharedPreferences=instance?: synchronized(lock){
            instance?: makePrivateSharedPreferences(context).also {
                instance=it
            }
        }
        private fun makePrivateSharedPreferences(context:Context):PrivateSharedPreferences{
            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }

    }
    fun saveTime(time:Long){
        sharedPreferences?.edit(commit=true){
            putLong(TIME,time)
        }
    }
    fun getTime()= sharedPreferences?.getLong(TIME,0)
}