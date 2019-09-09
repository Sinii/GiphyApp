package com.example.interfaces

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class OnPagingScrollListener(
    private val loadMore: () -> Unit,
    private val layoutManager: StaggeredGridLayoutManager,
    private val pageSize: Int
) : RecyclerView.OnScrollListener() {
    private val loadFactor = pageSize / 4

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
            val lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)

//            "onScrolled visibleItemCount = $visibleItemCount totalItemCount = $totalItemCount lastVisibleItemPosition = $lastVisibleItemPosition".dLog()
//            "onScrolled  visibleItemCount + lastVisibleItemPosition = ${visibleItemCount + lastVisibleItemPosition} totalItemCount - (pageSize / 3) = ${totalItemCount - (pageSize / 3)}".dLog()
//            "onScrolled  totalItemCount -  lastVisibleItemPosition = ${totalItemCount - lastVisibleItemPosition} loadFactor = $loadFactor".dLog()

            if (totalItemCount - lastVisibleItemPosition <= loadFactor
                && lastVisibleItemPosition >= 0
            ) {
                loadMore.invoke()
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}

