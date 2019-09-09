package com.anton.giflistfeature.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.anton.giflistfeature.R
import com.anton.giflistfeature.databinding.ItemGifBinding
import com.example.base.adapters.BaseAdapter
import com.example.gif.GifItem
import com.example.interfaces.GifItemClickListener
import com.example.utils.autoNotify
import java.util.*

class GifAdapter(
    items: ArrayList<GifItem>,
    private val listener: GifItemClickListener
) : BaseAdapter<GifViewHolder, GifItem>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = DataBindingUtil.inflate<ItemGifBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_gif,
            parent,
            false
        )
        return GifViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @Suppress("UNCHECKED_CAST")
    fun updateItems(list: List<GifItem>) {
        val oldItems = items.clone() as List<GifItem>
        clearItems()
        addItems(list)
        autoNotify(oldItems, items)
    }
}