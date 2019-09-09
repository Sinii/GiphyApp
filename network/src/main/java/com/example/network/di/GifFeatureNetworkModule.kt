package com.example.network.di

import com.example.network.retrofit.RetrofitGiphy
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class GifFeatureNetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitGiphy(retrofit: Retrofit): RetrofitGiphy = retrofit
        .create(RetrofitGiphy::class.java)
}