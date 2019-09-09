package com.example.repository.gif

import com.example.ApiError
import com.example.base.repository.BaseRepository
import com.example.gif.Gif
import com.example.network.retrofit.RetrofitGiphy
import javax.inject.Inject

class GifRepository
@Inject constructor(
    private val retrofitJobList: RetrofitGiphy
) : BaseRepository<GifRepository.Params, GifRepository.Result>() {
    override suspend fun doWork(params: Params): Result {
        val response =
            retrofitJobList.getTrendingGifsAsync(API_KEY2, params.limit, params.offset)
        val result = response.body()?.data
        val error = response.errorBody()
        return Result(result, getError(error))
    }

    class Params(val limit: Int, val offset: Int)
    class Result(val gifs: List<Gif>?, val error: ApiError?)

    companion object {
        const val API_KEY2 = "hLcRWdKsW2g8yEGgqIeN7YOzs6pl6ljm"
        const val API_KEY = "dc6zaTOxFJmzC"
    }
}