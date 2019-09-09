package com.example.repository.gif

import com.example.ApiError
import com.example.base.repository.BaseRepository
import com.example.gif.Gif
import com.example.network.retrofit.RetrofitGiphy
import com.example.repository.gif.GifRepository.Companion.API_KEY2
import javax.inject.Inject

class RandomGifRepository
@Inject constructor(
    private val retrofitJobList: RetrofitGiphy
) : BaseRepository<RandomGifRepository.Params, RandomGifRepository.Result>() {
    override suspend fun doWork(params: Params): Result {
        val response =
            retrofitJobList.getRandomGifsAsync(API_KEY2)
        val result = response.body()?.data
        val error = response.errorBody()
        return Result(result, getError(error))
    }

    class Params
    class Result(val gif: Gif?, val error: ApiError?)
}