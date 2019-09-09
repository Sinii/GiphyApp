package com.anton.giflistfeature.di


import com.anton.giflistfeature.ui.GifListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GifListFeatureFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeGifListFragment(): GifListFragment

    //Add more Fragments here

}
