package com.anton.giflistfeature.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.ViewModelCommands
import com.example.gif.GifItem
import com.example.usecase.gif.GetListOfTrendingGifsUseCase
import com.example.utils.dLog
import kotlinx.coroutines.Job
import java.util.*
import javax.inject.Inject

class GifListViewModel
@Inject constructor(
    private val getListOfTrendingGifsUseCase: GetListOfTrendingGifsUseCase
) : BaseViewModel() {
    val jobsList = MutableLiveData<List<GifItem>>()
    val showNoItemsStub = MutableLiveData(GONE)

    private var requestGifs: Job? = null
    @Volatile
    private var currentPage = 0

    override fun restoreViewModel() {
        "restoreViewModel".dLog()
        if (jobsList.value?.isNotEmpty() == true) {
            viewModelCommands.postValue(ViewModelCommands.DataLoaded)
        } else {
            doAutoMainWork()
        }
    }

    override fun doAutoMainWork() {
        "doAutoMainWork".dLog()
        doWork {
            requestGifs?.cancel()
            viewModelCommands.postValue(ViewModelCommands.DataStartLoading)
            requestGifs = getTrendingGifsJob(0)
            viewModelCommands.postValue(ViewModelCommands.DataLoaded)
        }
    }

    private fun getTrendingGifsJob(page: Int) = doWork {
        "getTrendingGifsJob ".dLog()
        val trendingResult = getListOfTrendingGifsUseCase
            .doWork(
                GetListOfTrendingGifsUseCase.Params(page)
            )
        val items = trendingResult.items
        val hasItems = !items.isNullOrEmpty()
        if (hasItems) {
            showNoItemsStub.postValue(GONE)
            val newItems = arrayListOf<GifItem>()
            jobsList.value?.let {
                newItems.addAll(it as ArrayList<GifItem>)
                newItems.forEach {
                    "1 ${it.id}".dLog()

                }
            }
            newItems.addAll(items as ArrayList<GifItem>)
            items.forEach {
                "2 ${it.id}".dLog()
            }
            "getTrendingGifsJob jobsList.value ${jobsList.value?.size}".dLog()
            "getTrendingGifsJob items ${items.size}".dLog()
            "getTrendingGifsJob newItems ${newItems.size}".dLog()

//            Collections.copy(newItems, jobsList.value)
//            Collections.copy(newItems, items)
            jobsList.postValue(newItems)
        } else {
            showError(trendingResult.errorMessage)
            showNoItemsStub.postValue(VISIBLE)
        }
    }

    fun loadMore() {
        if (requestGifs?.isCompleted == true) {
            currentPage++
            "loadMore page = $currentPage requestGifs?.isCompleted = ${requestGifs?.isCompleted} requestGifs?.isActive = ${requestGifs?.isActive}".dLog()
            requestGifs = getTrendingGifsJob(currentPage)
        }
    }
}