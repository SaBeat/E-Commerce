package com.example.e_commerce.common.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.e_commerce.R

fun ImageView.downloadToImageView(
  imageUrl:String,
  @DrawableRes errorImage:Int = R.drawable.error_image
){
    Glide.with(this.context).load(imageUrl).error(errorImage).into(this)
}