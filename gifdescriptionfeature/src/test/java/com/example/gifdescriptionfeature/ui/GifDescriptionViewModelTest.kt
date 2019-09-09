package com.example.gifdescriptionfeature.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gif.GifItem
import com.example.usecase.gif.GetRandomGifUseCase
import io.mockk.mockkClass
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class GifDescriptionViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `check job details data`() {
        val job = GifItem(
            "1", "2", "3", 1, 2, 3, 4
        )
        val useCase = mockkClass(GetRandomGifUseCase::class)
        val vm = GifDescriptionViewModel(useCase)
        vm.gifItem = job
        vm.doAutoMainWork()

        Assert.assertTrue(vm.gifUrl.value == job.gifUrl)
    }
}