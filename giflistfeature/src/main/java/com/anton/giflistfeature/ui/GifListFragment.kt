package com.anton.giflistfeature.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anton.giflistfeature.R
import com.anton.giflistfeature.databinding.FragmentGifListBinding
import com.anton.giflistfeature.ui.adapter.GifAdapter
import com.example.base.di.ViewModelFactory
import com.example.base.ui.BaseFragment
import com.example.gif.GifItem
import com.example.interfaces.GifItemClickListener
import com.example.interfaces.OnPagingScrollListener
import com.example.usecase.gif.GetListOfTrendingGifsUseCase


class GifListFragment : BaseFragment<FragmentGifListBinding, ViewModelFactory>() {

    override fun provideListOfViewModels(): Array<Class<*>> = arrayOf(
        GifListViewModel::class.java
    )

    override fun provideActionsBinding(): (FragmentGifListBinding, Set<*>) -> Unit =
        { binding, viewModelList ->
            viewModelList.forEach { viewModel ->
                when (viewModel) {
                    is GifListViewModel -> {
                        binding.vm = viewModel
                        val spanCount = 2
                        val layoutManager = StaggeredGridLayoutManager(
                            spanCount,
                            StaggeredGridLayoutManager.VERTICAL
                        )
                        val adapter = GifAdapter(ArrayList(), object : GifItemClickListener {
                            override fun onClick(gifItem: GifItem, sharedView: View?) {
                                val bundle = Bundle()
                                    .apply {
                                        putParcelable(GifItem::class.toString(), gifItem)
                                    }
                                findNavController()
                                    .navigate(
                                        R.id.action_gifListFragment_to_gifDescriptionFragment,
                                        bundle
                                    )
                            }
                        })
                        binding.itemsRecyclerView.layoutManager = layoutManager
                        binding.itemsRecyclerView.adapter = adapter
                        binding.itemsRecyclerView.addOnScrollListener(
                            OnPagingScrollListener(
                                { viewModel.loadMore() },
                                layoutManager,
                                GetListOfTrendingGifsUseCase.ITEMS_PAGINATION_COUNT
                            )
                        )
                        viewModel.gifList.observe(this, Observer { list ->
                            adapter.updateItems(list)
                        })
                    }
                }
            }
        }

    override fun provideLayout() = R.layout.fragment_gif_list

    override fun provideLifecycleOwner() = this
}