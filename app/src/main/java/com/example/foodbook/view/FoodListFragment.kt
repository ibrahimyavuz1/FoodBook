package com.example.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodbook.R
import com.example.foodbook.adapter.FoodRecyclerAdapter
import com.example.foodbook.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*


class FoodListFragment : Fragment() {

private lateinit var viewModel:FoodListViewModel
private val recyclerFoodAdapter=FoodRecyclerAdapter(arrayListOf()) //NULL
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()
        foodListRecycler.layoutManager=LinearLayoutManager(context)
        foodListRecycler.adapter=recyclerFoodAdapter
        swipeRefreshLayout.setOnRefreshListener {
            foodListProgressBar.visibility=View.VISIBLE
            foodErrorMessage.visibility=View.GONE
            foodListRecycler.visibility=View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing=false
        }
        observeLiveData()
    }
    fun observeLiveData(){
        viewModel.foods.observe(viewLifecycleOwner, Observer{foods->
            foods?.let {
               foodListRecycler.visibility=View.VISIBLE
                recyclerFoodAdapter.updateFoodList(foods)
            }

        })
        viewModel.foodErrorMessage.observe(viewLifecycleOwner,Observer{error->
            error?.let {
                if(it){
                    foodErrorMessage.visibility=View.VISIBLE
                    foodListRecycler.visibility=View.GONE
                }
                else{
                    foodErrorMessage.visibility=View.GONE
                }
            }

        })
        viewModel.foodLoadingProgressBar.observe(viewLifecycleOwner,Observer{loading->
            loading?.let {
                if(it){
                    foodListRecycler.visibility=View.GONE
                    foodErrorMessage.visibility=View.GONE
                    foodListProgressBar.visibility=View.VISIBLE
                }
                else{
                    foodListProgressBar.visibility=View.GONE
                }
            }

        })
    }
    
}