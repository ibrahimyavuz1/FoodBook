package com.example.foodbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.foodbook.R
import com.example.foodbook.databinding.FoodRecyclerRowBinding
import com.example.foodbook.model.Food
import com.example.foodbook.util.DownloadImage
import com.example.foodbook.util.MakeAPlaceHolder
import com.example.foodbook.view.FoodListFragmentDirections
import com.google.android.material.internal.NavigationSubMenu
import kotlinx.android.synthetic.main.food_recycler_row.view.*

class FoodRecyclerAdapter(val foodList:ArrayList<Food>):RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>(),FoodClickListener {

    class FoodViewHolder(var view: FoodRecyclerRowBinding):RecyclerView.ViewHolder(view.root){ // We renamed "itemView" to "view" because View class onject and FoodRecyclerRowBinding class were overlapped.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //val view=inflater.inflate(R.layout.food_recycler_row,parent,false)
        val view =  DataBindingUtil.inflate<FoodRecyclerRowBinding>(inflater,R.layout.food_recycler_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.view.food=foodList[position]
        holder.view.listener=this
        /*
        holder.itemView.name.text=foodList.get(position).foodName
        holder.itemView.calorie.text=foodList.get(position).foodCalorie
        holder.itemView.imageView.DownloadImage(foodList.get(position).foodImage, MakeAPlaceHolder(holder.itemView.context))
        holder.itemView.setOnClickListener {
            val action=FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)

        }

         */

    }

    override fun getItemCount(): Int {
        return foodList.size
    }
    fun updateFoodList(newFoodList:List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun clickedFood(view: View) {
        val uuid =view.food_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action=FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }
}