package com.example.foodbook.service

import com.example.foodbook.model.Food
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class FoodAPIService {
//  https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
//   BASE_URL -> https://raw.githubusercontent.com/
//   atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json


    private val BASE_URL="https://raw.githubusercontent.com/"
    private val api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FoodAPI::class.java)
    fun getData(): Single<List<Food>> {
        return api.getFood()
    }


}