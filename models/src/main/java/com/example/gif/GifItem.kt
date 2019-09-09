package com.example.gif

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GifItem(
    val id: String,
    val previewUrl: String,
    val gifUrl: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val gifWidth: Int,
    val gifHeight: Int
) : Parcelable