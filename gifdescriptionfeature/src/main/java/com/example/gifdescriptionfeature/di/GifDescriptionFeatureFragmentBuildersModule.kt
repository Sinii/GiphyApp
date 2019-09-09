package com.example.gifdescriptionfeature.di


import com.example.gifdescriptionfeature.ui.GifDescriptionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GifDescriptionFeatureFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeGifDescriptionFragment(): GifDescriptionFragment

    //Add more Fragments here

}
