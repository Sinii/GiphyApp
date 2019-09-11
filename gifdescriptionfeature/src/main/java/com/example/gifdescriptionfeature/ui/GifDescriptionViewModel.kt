package com.example.gifdescriptionfeature.ui

import androidx.lifecycle.MutableLiveData
import com.example.base.viewmodel.BaseViewModel
import com.example.gif.GifItem
import com.example.usecase.gif.GetRandomGifUseCase
import com.example.utils.dLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import javax.inject.Inject

class GifDescriptionViewModel
@Inject constructor(
    private val getRandomGifUseCase: GetRandomGifUseCase
) : BaseViewModel() {
    var screenWidth: Int? = null
    var gifItem: GifItem? = null
    val gifUrl = MutableLiveData<String>()
    val gifDimension = MutableLiveData<Pair<Int, Int>>()

    private var infinityRandomJob: Job? = null

    override fun doAutoMainWork() {
        infinityRandomJob?.cancel()
        infinityRandomJob = newInfinityRandomJob()
    }

    private fun updateDimensions(item: GifItem) {
        val width = screenWidth ?: item.gifWidth
        val height = ((screenWidth ?: item.gifWidth) / item.gifWidth) * item.gifHeight
        "updateDimensions width = $width height = $height".dLog()
        gifDimension.postValue(width to height)
    }

    private fun newInfinityRandomJob() = doWork {
        while (isActive) {
            gifItem?.let {
                gifUrl.postValue(it.gifUrl)
                updateDimensions(it)
            }
            delay(10000L)
            val result = getRandomGifUseCase
                .doWork(GetRandomGifUseCase.Params())
            gifItem = result.item
            result.errorMessage?.let {
                showError(it)
            }
        }
    }

    override fun onCleared() {
        infinityRandomJob?.cancel()
        infinityRandomJob = null
        super.onCleared()
    }
}