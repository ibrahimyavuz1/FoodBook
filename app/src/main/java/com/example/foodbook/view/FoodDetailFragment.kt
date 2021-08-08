package com.example.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.foodbook.R
import com.example.foodbook.util.DownloadImage
import com.example.foodbook.util.MakeAPlaceHolder
import com.example.foodbook.viewmodel.FoodDetailViewModel
import kotlinx.android.synthetic.main.fragment_food_detail.*


class FoodDetailFragment : Fragment() {
    private var foodId=0
    private lateinit var  viewModel:FoodDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            foodId=FoodDetailFragmentArgs.fromBundle(it).foodId
        }
        viewModel=ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.takeRoomData(foodId)

        observeLiveData()
    }
    fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer{food->
            food?.let{
                foodName.text=it.foodName
                foodCalorie.text=it.foodCalorie
                foodCarbohydrate.text=it.foodCarbonhydrate
                foodProtein.text=it.foodProtein
                foodFat.text=it.foodFat
                context?.let {
                    foodImage.DownloadImage(food.foodImage, MakeAPlaceHolder(it))
                }
            }
        })
    }
}