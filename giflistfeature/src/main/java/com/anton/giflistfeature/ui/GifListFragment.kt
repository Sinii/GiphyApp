package com.anton.giflistfeature.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anton.giflistfeature.R
import com.anton.giflistfeature.databinding.FragmentGifListBinding
import com.anton.giflistfeature.ui.adapter.GifAdapter
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.example.base.di.ViewModelFactory
import com.example.base.ui.BaseFragment
import com.example.base.viewmodel.ViewModelCommands
import com.example.gif.GifItem
import com.example.interfaces.GifItemClickListener
import com.example.interfaces.OnPagingScrollListener
import com.example.usecase.gif.GetListOfTrendingGifsUseCase
import com.example.utils.dLog
import com.example.utils.toSharedPair


class GifListFragment : BaseFragment<FragmentGifListBinding, ViewModelFactory>() {
    private var skeletonScreen: RecyclerViewSkeletonScreen? = null

    override fun provideListOfViewModels(): Array<Class<*>> = arrayOf(
        GifListViewModel::class.java
    )

    override fun busEvents(
        command: ViewModelCommands,
        viewModelList: Set<*>,
        binding: FragmentGifListBinding
    ): Boolean {
        viewModelList.forEach { viewModel ->
            when {
                command is ViewModelCommands.DataStartLoading
                        && viewModel is GifListViewModel -> {
                    "DataStartLoading".dLog()
                    //skeletonScreen?.show()
                }
                command is ViewModelCommands.DataLoaded
                        && viewModel is GifListViewModel -> {
                    "DataLoaded".dLog()

                    skeletonScreen?.hide()
                }
            }
        }
        return super.busEvents(command, viewModelList, binding)
    }

    override fun provideActionsBinding(): (FragmentGifListBinding, Set<*>) -> Unit =
        { binding, viewModelList ->
            viewModelList.forEach { viewModel ->
                when (viewModel) {
                    is GifListViewModel -> {
                        binding.vm = viewModel
                        val layoutManager =
                            StaggeredGridLayoutManager(
                                2,
                                StaggeredGridLayoutManager.VERTICAL
                            )


                        val adapter = GifAdapter(ArrayList(), object : GifItemClickListener {
                            @SuppressLint("ResourceType")
                            override fun onClick(gifItem: GifItem, sharedView: View?) {
                                val extras = if (sharedView != null)
                                    FragmentNavigatorExtras(
                                        sharedView toSharedPair SHARED_LOGO_VIEW_KEY
                                    )
                                else
                                    null
                                val bundle = Bundle()
                                    .apply {
                                        putParcelable(GifItem::class.toString(), gifItem)
                                    }
                                if (extras != null) {
                                    //todo fix id
                                    findNavController()
                                        .navigate(
                                            R.id.action_gifListFragment_to_gifDescriptionFragment,
                                            bundle,
                                            null,
                                            extras
                                        )
                                } else {
                                    findNavController()
                                        .navigate(
                                            R.id.action_gifListFragment_to_gifDescriptionFragment,
                                            bundle
                                        )
                                }
                            }
                        })
                        binding.itemsRecyclerView.layoutManager = layoutManager
                        binding.itemsRecyclerView.adapter = adapter

                        binding.itemsRecyclerView.addOnScrollListener(
                            OnPagingScrollListener(
                                { viewModel.loadMore() },
                                layoutManager,
                                GetListOfTrendingGifsUseCase.ITEMS_LIMIT
                            )
                        )
                        viewModel.jobsList.observe(this, Observer { list ->
                            adapter.updateItems(list)
                        })
                        skeletonScreen = Skeleton
                            .bind(binding.itemsRecyclerView)
                            .adapter(adapter)
                            .load(R.layout.item_gif_skeleton)
                            .angle(0)
                            .color(R.color.shineLoadingColor)
                            .count(STUB_ITEMS_COUNT)
                            .show()
                    }
                }
            }
        }

    override fun provideLayout() = R.layout.fragment_gif_list

    override fun provideLifecycleOwner() = this

    companion object {
        const val STUB_ITEMS_COUNT = 15
        const val SHARED_LOGO_VIEW_KEY = "_image_transition"
    }
}