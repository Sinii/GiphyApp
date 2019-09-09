package com.example.gif

import com.squareup.moshi.Json

class Images(
    @field: Json(name = "480w_still")
    val previewMedia: Media? = null,
    val downsized: Media? = null,
    val original_mp4: Media? = null
)