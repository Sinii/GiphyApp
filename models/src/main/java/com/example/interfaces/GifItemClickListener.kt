package com.example.interfaces

import android.view.View
import com.example.gif.GifItem

interface GifItemClickListener {

    fun onClick(gifItem: GifItem, sharedView: View?)
}