package com.example.usecase.gif

import com.example.gif.Gif
import com.example.gif.GifItem
import com.example.gif.Images
import com.example.gif.Media
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class ConvertGifToGifItemUseCaseTest {

    @Test
    fun `check converter empty state`() {
        val receivedGif = Gif("", null)
        val waitedGif = GifItem("", "", "", 0, 0, 0, 0)
        val useCase = ConvertGifToGifItemUseCase()
        runBlocking {
            val resultGif = useCase
                .doWork(ConvertGifToGifItemUseCase.Params(receivedGif))
                .convertedGif
            Assert.assertTrue(resultGif == waitedGif)
        }
    }

    @Test
    fun `check converter correct state`() {
        val receivedGif = Gif(
            "0", Images(
                original_mp4 = Media(url = "1", mp4 = "2", width = "3", height = "4"),
                previewMedia = Media(url = "5", mp4 = "6", width = "7", height = "8")
            )
        )
        val waitedGif = GifItem(
            receivedGif.id,
            receivedGif.images!!.previewMedia!!.url!!,
            receivedGif.images!!.original_mp4!!.mp4!!,
            receivedGif.images!!.previewMedia!!.width.toInt(),
            receivedGif.images!!.previewMedia!!.height.toInt(),
            receivedGif.images!!.original_mp4!!.width.toInt(),
            receivedGif.images!!.original_mp4!!.height.toInt()
        )
        val useCase = ConvertGifToGifItemUseCase()
        runBlocking {
            val resultGif = useCase
                .doWork(ConvertGifToGifItemUseCase.Params(receivedGif))
                .convertedGif
            Assert.assertTrue(resultGif == waitedGif)
        }
    }

}