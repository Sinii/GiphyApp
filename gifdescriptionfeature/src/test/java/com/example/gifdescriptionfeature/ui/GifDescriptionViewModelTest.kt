package com.example.gifdescriptionfeature.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gif.GifItem
import com.example.usecase.gif.GetRandomGifUseCase
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GifDescriptionViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        mockkStatic(Dispatchers::class)
        every {
            Dispatchers.IO
        } returns Dispatchers.Main
    }

    @Test
    fun `check gif details data`() {
        val gifItem = GifItem(
            "1", "2", "3", 1, 2, 3, 4
        )
        val useCase = mockkClass(GetRandomGifUseCase::class)
        val vm = GifDescriptionViewModel(useCase)
        vm.gifItem = gifItem
        vm.doAutoMainWork()

        Assert.assertTrue(vm.gifUrl.value == gifItem.gifUrl)
    }
}