package com.raantech.mycups.ui.more.media.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.media.Media
import com.raantech.mycups.databinding.RowImageViewBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class MediaRecyclerAdapter(
        context: Context
) : BaseBindingRecyclerViewAdapter<Media>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(
                RowImageViewBinding.inflate(
                        LayoutInflater.from(context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ImageViewHolder(private val binding: RowImageViewBinding) :
            BaseViewHolder<Media>(binding.root) {

        override fun bind(item: Media) {
            binding.item = item
            binding.imgRemove.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
            binding.relativePreview.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
            binding.relativePreview.setOnLongClickListener {
                itemClickListener?.onItemLongClick(it, bindingAdapterPosition, item)
                return@setOnLongClickListener true
            }
        }
    }

}