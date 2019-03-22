package com.textingstory.view.util

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide

class PictureHelper {
    fun setupImageView(activity: Activity, url: String, imageView: ImageView) =
        Glide.with(activity)
            .asDrawable()
            .load(url)
            .into(imageView)
}