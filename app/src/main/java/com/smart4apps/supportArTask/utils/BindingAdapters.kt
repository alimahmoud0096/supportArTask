package com.smart4apps.supportArTask.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView,url:String?) {
    url?.let {
        imageView.visibility=View.VISIBLE
        Picasso.get().load(url).into(imageView)
    }?: run {
        imageView.visibility=View.GONE
    }

}

