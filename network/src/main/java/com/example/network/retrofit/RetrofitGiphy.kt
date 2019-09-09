package com.example.network.retrofit

import com.example.gif.GifRandomResponse
import com.example.gif.GifResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitGiphy {

    @GET("trending")
    suspend fun getTrendingGifsAsync(
        @Query("api_key") api_key: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<GifResponse>

    @GET("random")
    suspend fun getRandomGifsAsync(
        @Query("api_key") api_key: String
    ): Response<GifRandomResponse>

}
