package com.example.foodbook.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbook.model.Food
import com.example.foodbook.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application): BaseViewModel(application){
    val foodLiveData=MutableLiveData<Food>()
    fun takeRoomData(uuid:Int){
        launch {
            val dao =FoodDatabase(getApplication()).foodDao()
            val food=dao.getFood(uuid)
            foodLiveData.value=food
            }
    }
}