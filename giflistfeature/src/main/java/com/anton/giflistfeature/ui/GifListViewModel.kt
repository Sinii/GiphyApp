package com.anton.giflistfeature.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import com.example.base.viewmodel.BaseViewModel
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
    val gifList = MutableLiveData<List<GifItem>>()
    val showNoItemsStub = MutableLiveData(GONE)
    val showRecyclerView = MutableLiveData(GONE)
    val showLoadingProgress = MutableLiveData(VISIBLE)

    private var requestGifs: Job? = null
    @Volatile
    private var currentPage = 0

    override fun doAutoMainWork() {
        doWork {
            showLoadingProgress.postValue(VISIBLE)
            requestGifs?.cancel()
            currentPage = 0
            requestGifs = getTrendingGifsJob(0)
        }
    }

    override fun restoreViewModel() {
        val noItems = gifList.value?.isNotEmpty() != true
        "noItems  = $noItems".dLog()
        if (noItems) doAutoMainWork() else showItemsState()
    }

    private fun getTrendingGifsJob(page: Int) = doWork {
        "getTrendingGifsJob ".dLog()
        val trendingResult = getListOfTrendingGifsUseCase
            .doWork(GetListOfTrendingGifsUseCase.Params(page))
        val items = trendingResult.items
        val hasItems = !items.isNullOrEmpty()
        if (hasItems) {
            showItemsState()
            val newItems = arrayListOf<GifItem>()
            gifList.value?.let {
                newItems.addAll(it as ArrayList<GifItem>)
            }
            newItems.addAll(items as ArrayList<GifItem>)
            gifList.postValue(newItems)
        } else {
            showNoItemsState()
            showError(trendingResult.errorMessage)
        }
    }

    fun loadMore() {
        if (requestGifs?.isCompleted == true) {
            currentPage++
            "loadMore page = $currentPage requestGifs?.isCompleted = ${requestGifs?.isCompleted} requestGifs?.isActive = ${requestGifs?.isActive}".dLog()
            requestGifs = getTrendingGifsJob(currentPage)
        }
    }

    private fun showNoItemsState() {
        showNoItemsStub.postValue(VISIBLE)
        showRecyclerView.postValue(GONE)
        showLoadingProgress.postValue(GONE)
    }

    private fun showItemsState() {
        showNoItemsStub.postValue(GONE)
        showRecyclerView.postValue(VISIBLE)
        showLoadingProgress.postValue(GONE)
    }
}