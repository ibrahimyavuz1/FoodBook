package com.example.foodbook.service

import com.example.foodbook.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {
 //  https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
 // You must click raw option on github to be able to use.
//   BASE_URL -> https://raw.githubusercontent.com/
//   atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
 @GET(" atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
 fun getFood(): Single<List<Food>>

}