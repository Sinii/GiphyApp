package com.example.utils

import android.media.MediaPlayer
import android.net.Uri
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url)
}

@BindingAdapter("webmUrl")
fun loadWebm(view: VideoView, url: String?) {
    url?.let {
        view.setVideoURI(Uri.parse(url))
        view.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
            mp.isLooping = true
            view.start()
        })
    }
}


@BindingAdapter("setDrawableResource")
fun setDrawableResource(view: ImageView, resource: Int?) {
    if (resource != null) {
        view.setImageDrawable(view.context.getDrawable(resource))
    }
}

@BindingAdapter("textChangedListener")
fun setTextChangedListener(appCompatEditText: AppCompatEditText, listener: TextWatcher?) {
    "setTextChangedListener $listener".dLog()
    if (listener != null) {
        "setTextChangedListener ".dLog()
        appCompatEditText.addTextChangedListener(listener)
    }
}