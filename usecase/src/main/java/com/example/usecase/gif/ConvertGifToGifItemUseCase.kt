package com.example.usecase.gif

import com.example.base.usecase.BaseUseCase
import com.example.gif.Gif
import com.example.gif.GifItem
import javax.inject.Inject

class ConvertGifToGifItemUseCase
@Inject constructor() :
    BaseUseCase<ConvertGifToGifItemUseCase.Params, ConvertGifToGifItemUseCase.Result>() {
    override suspend fun doWork(params: Params): Result {
        val gif = params.gif
        return Result(
            GifItem(
                gif.id,
                gif.images?.previewMedia?.url ?: "",
                gif.images?.original_mp4?.mp4 ?: "",
                gif.images?.previewMedia?.width?.toInt() ?: 0,
                gif.images?.previewMedia?.height?.toInt() ?: 0,
                gif.images?.original_mp4?.width?.toInt() ?: 0,
                gif.images?.original_mp4?.height?.toInt() ?: 0
            )
        )
    }

    class Params(val gif: Gif)
    class Result(val convertedGif: GifItem)
}