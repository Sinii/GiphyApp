package com.example.usecase.gif

import com.example.base.usecase.BaseUseCase
import com.example.gif.GifItem
import com.example.repository.gif.RandomGifRepository
import javax.inject.Inject

class GetRandomGifUseCase
@Inject constructor(
    private val randomGifRepository: RandomGifRepository,
    private val convertGifToGifItemUseCase: ConvertGifToGifItemUseCase
) : BaseUseCase<GetRandomGifUseCase.Params, GetRandomGifUseCase.Result>() {
    override suspend fun doWork(params: Params): Result {
        val result = randomGifRepository
            .doWork(RandomGifRepository.Params())
        val gif = result.gif
        val gifItem = gif?.let {
            convertGifToGifItemUseCase
                .doWork(ConvertGifToGifItemUseCase.Params(it))
                .convertedGif
        }
        return Result(gifItem, result.error?.message)
    }

    class Params
    class Result(val item: GifItem?, val errorMessage: String?)
}