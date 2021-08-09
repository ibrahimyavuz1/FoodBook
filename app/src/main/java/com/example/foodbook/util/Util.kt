package com.example.foodbook.util

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ImageViewBindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodbook.R

fun ImageView.DownloadImage(url:String?, placeholder:CircularProgressDrawable){
    val options= RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}
fun MakeAPlaceHolder(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()
    }
}
@BindingAdapter("android:downloadImage")
fun downloadImageForBinding(view:ImageView,url:String?){
    view.DownloadImage(url, MakeAPlaceHolder(view.context))
}