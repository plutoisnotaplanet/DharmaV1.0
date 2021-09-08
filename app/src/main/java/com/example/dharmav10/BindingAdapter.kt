package com.example.dharmav10

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .apply(RequestOptions.placeholderOf(R.drawable.placeholder_background)).error(R.drawable.placeholder_foreground)
        .into(imageView)
}