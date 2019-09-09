package com.example.gifdescriptionfeature.ui

import androidx.lifecycle.Observer
import com.example.base.di.ViewModelFactory
import com.example.base.ui.BaseFragment
import com.example.gif.GifItem
import com.example.gifdescriptionfeature.R
import com.example.gifdescriptionfeature.databinding.FragmentGifDescriptionBinding
import com.example.utils.dLog

class GifDescriptionFragment : BaseFragment<FragmentGifDescriptionBinding, ViewModelFactory>() {

    override fun provideListOfViewModels(): Array<Class<*>> = arrayOf(
        GifDescriptionViewModel::class.java
    )

    override fun provideActionsBinding(): (FragmentGifDescriptionBinding, Set<*>) -> Unit =
        { binding, viewModelList ->
            "provideActionsBinding".dLog()

            viewModelList.forEach { viewModel ->
                when (viewModel) {
                    is GifDescriptionViewModel -> {
                        "provideActionsBinding GifDescriptionViewModel".dLog()
                        viewModel.gifItem =
                            arguments?.getParcelable(GifItem::class.toString())

                        viewModel.screenWidth = this.resources.displayMetrics.widthPixels
                        viewModel.gifDimension.observe(this, Observer {
                            binding.videoView.layoutParams.width = it.first
                            binding.videoView.layoutParams.height = it.second
                        })
                        binding.vm = viewModel
                    }
                }
            }
        }

    override fun provideLayout() = R.layout.fragment_gif_description

    override fun provideLifecycleOwner() = this

}