package com.example.di.module

import com.anton.giflistfeature.di.GifListFeatureFragmentBuildersModule
import com.anton.giflistfeature.di.GifListFeatureViewModelModule
import com.example.gifdescriptionfeature.di.GifDescriptionFeatureFragmentBuildersModule
import com.example.gifdescriptionfeature.di.GifDescriptionFeatureViewModelModule
import com.example.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        GifListFeatureViewModelModule::class,
        GifDescriptionFeatureViewModelModule::class
    ]
)
abstract class MainActivityModule {

    @ContributesAndroidInjector(
        modules = [
            GifListFeatureFragmentBuildersModule::class,
            GifDescriptionFeatureFragmentBuildersModule::class
        ]
    )

    abstract fun contributeMainActivity(): MainActivity

}