package com.example.gifdescriptionfeature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.base.di.ViewModelFactory
import com.example.base.di.ViewModelKey
import com.example.gifdescriptionfeature.ui.GifDescriptionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GifDescriptionFeatureViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GifDescriptionViewModel::class)
    internal abstract fun bindGifDescriptionViewModel(viewModel: GifDescriptionViewModel): ViewModel

    //Add more ViewModels here
}