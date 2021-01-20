package com.example.orfdownloader.util

import android.content.res.Resources
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.orfdownloader.R
import com.squareup.picasso.Picasso
import java.util.*

@BindingAdapter("bind:imageUrl")
fun setImageUrl(view: ImageView, imageUrl: TreeMap<Int, String>?) {
    val width = Resources.getSystem().displayMetrics.widthPixels
    Picasso.get()
        .load((imageUrl?.ceilingEntry(width)?: imageUrl?.lastEntry())?.value)
        .fit()
        .centerCrop()
        .placeholder(R.drawable.ic_radiotowerbg)
        .into(view)
}