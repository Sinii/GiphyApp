package com.anton.giflistfeature.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.anton.giflistfeature.databinding.ItemGifBinding
import com.example.gif.GifItem
import com.example.interfaces.GifItemClickListener
import com.example.utils.loadImage
import kotlinx.android.synthetic.main.item_gif.view.*

class GifViewHolder(binding: ItemGifBinding, private val listener: GifItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GifItem?) {
        with(itemView) {
            if (item != null) {
                gifItemLayout.setOnClickListener {
                    listener.onClick(item, imageView)
                }
                imageView.layoutParams.height = item.previewHeight
                imageView.layoutParams.width = item.previewWidth
                imageView.loadImage(item.previewUrl)
            }
        }
    }
}