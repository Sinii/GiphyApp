package com.example.usecase.gif

import com.example.base.usecase.BaseUseCase
import com.example.gif.GifItem
import com.example.repository.gif.GifRepository
import javax.inject.Inject

class GetListOfTrendingGifsUseCase
@Inject constructor(
    private val gifRepository: GifRepository,
    private val convertGifToGifItemUseCase: ConvertGifToGifItemUseCase
) : BaseUseCase<GetListOfTrendingGifsUseCase.Params, GetListOfTrendingGifsUseCase.Result>() {
    override suspend fun doWork(params: Params): Result {
        val offset = ITEMS_LIMIT * params.page
        val result = gifRepository
            .doWork(GifRepository.Params(ITEMS_LIMIT, offset))
        val gifs = result
            .gifs
            ?.map {
                return@map convertGifToGifItemUseCase
                    .doWork(ConvertGifToGifItemUseCase.Params(it))
                    .convertedGif
            }
        return Result(gifs, result.error?.message)
    }

    class Params(val page: Int)
    class Result(val items: List<GifItem>?, val errorMessage: String?)

    companion object {
        const val ITEMS_LIMIT = 30
    }
}