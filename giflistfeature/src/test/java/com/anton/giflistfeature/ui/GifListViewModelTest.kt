package com.anton.giflistfeature.ui

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gif.GifItem
import com.example.usecase.gif.GetListOfTrendingGifsUseCase
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GifListViewModelTest {
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
    fun `check no gifs state`() {
        val useCase = mockkClass(GetListOfTrendingGifsUseCase::class)
        every {
            runBlocking {
                useCase.doWork(any())
            }
        } returns GetListOfTrendingGifsUseCase.Result(null, null)
        val vm = GifListViewModel(useCase)
        vm.doAutoMainWork()
        Assert.assertTrue(vm.showNoItemsStub.value == View.VISIBLE)
    }

    @Test
    fun `check has gifs state`() {
        val gifItem = GifItem(
            "1", "2", "3", 1, 2, 3, 4
        )
        val useCase = mockkClass(GetListOfTrendingGifsUseCase::class)
        every {
            runBlocking {
                useCase.doWork(any())
            }
        } returns GetListOfTrendingGifsUseCase.Result(arrayListOf(gifItem), null)
        val vm = GifListViewModel(useCase)
        vm.doAutoMainWork()
        Assert.assertTrue(vm.showNoItemsStub.value == View.GONE)
    }
}