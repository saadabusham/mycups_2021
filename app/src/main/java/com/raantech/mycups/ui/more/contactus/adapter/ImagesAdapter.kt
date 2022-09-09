package com.raantech.mycups.ui.more.contactus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.media.Media
import com.raantech.mycups.databinding.RowAddImageBinding
import com.raantech.mycups.databinding.RowImageViewSmallBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class ImagesAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Media>(context) {
    companion object {
        const val ADD_IMAGE = 1
        const val IMAGE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].id == null) ADD_IMAGE else IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IMAGE -> ImageViewHolder(
                RowImageViewSmallBinding.inflate(
                    LayoutInflater.from(context), parent, false
                )
            )
            else -> {
                AddImageViewHolder(
                    RowAddImageBinding.inflate(
                        LayoutInflater.from(context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            holder.bind(items[position])
        } else if (holder is AddImageViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ImageViewHolder(private val binding: RowImageViewSmallBinding) :
        BaseViewHolder<Media>(binding.root) {

        override fun bind(item: Media) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }

    inner class AddImageViewHolder(private val binding: RowAddImageBinding) :
        BaseViewHolder<Media?>(binding.root) {

        override fun bind(item: Media?) {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, "")
            }
        }
    }


}