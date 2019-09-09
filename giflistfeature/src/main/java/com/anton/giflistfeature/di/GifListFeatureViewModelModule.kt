package com.anton.giflistfeature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anton.giflistfeature.ui.GifListViewModel
import com.example.base.di.ViewModelFactory
import com.example.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GifListFeatureViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GifListViewModel::class)
    internal abstract fun bindGifListViewModel(viewModel: GifListViewModel): ViewModel

    //Add more ViewModels here
}