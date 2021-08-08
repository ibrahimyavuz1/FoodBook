package com.example.foodbook.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbook.model.Food
import com.example.foodbook.service.FoodAPIService
import com.example.foodbook.service.FoodDatabase
import com.example.foodbook.util.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application):BaseViewModel(application) {
    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoadingProgressBar = MutableLiveData<Boolean>()
    private var updateTime= 10*60*1000*1000*1000L

    private val foodApiService=FoodAPIService()
    private val disposable=CompositeDisposable()
    private val privateSharedPreferences= PrivateSharedPreferences(getApplication())
    fun refreshData() {
        val saveTime=privateSharedPreferences.getTime()
        if(saveTime!=null&&saveTime!=0L&&System.nanoTime()-saveTime<updateTime){
            takeDatasFromSqlite()
        }
        else{
        takeDatasFromInternet()
        }
    }
    fun refreshFromInternet(){
        takeDatasFromInternet()
    }
    fun takeDatasFromSqlite(){
        foodLoadingProgressBar.value=true
        launch {
            val foodList=FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodList)
            Toast.makeText(getApplication(),"It took data from Room",Toast.LENGTH_LONG).show()
        }
    }

    fun takeDatasFromInternet(){

        foodLoadingProgressBar.value= true
        disposable.add(
            foodApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        memorizeSqlite(t)
                        Toast.makeText(getApplication(),"It took data from Internet",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        foodErrorMessage.value=true
                        foodLoadingProgressBar.value=false
                        e.printStackTrace() // It is for any problem which is here to see in Logcat!
                    }

                })
        )
    }
    fun showFoods(foodList:List<Food>){
        foods.value=foodList
        foodLoadingProgressBar.value=false
        foodErrorMessage.value=false
    }
    fun memorizeSqlite(foodList:List<Food>){
        launch {
            val dao= FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            // *foodList.toTypedArray() --> This is to shred foodList to one by one food.
            val uuidList=dao.insertAll(*foodList.toTypedArray())
            var i =0
            while(i<foodList.size){
                foodList[i].uuid=uuidList[i].toInt()
                i= i+1
            }
            showFoods(foodList)
        }
        privateSharedPreferences.saveTime(System.nanoTime())
    }
}