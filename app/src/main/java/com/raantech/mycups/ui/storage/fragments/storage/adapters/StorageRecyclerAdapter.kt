package com.raantech.mycups.ui.storage.fragments.storage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.storage.Storage
import com.raantech.mycups.databinding.RowOfferProductBinding
import com.raantech.mycups.databinding.RowStorageBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class StorageRecyclerAdapter constructor(
    context: Context
) : BaseBindingRecyclerViewAdapter<Storage>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowStorageBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowStorageBinding) :
        BaseViewHolder<Storage>(binding.root) {

        override fun bind(item: Storage) {
            binding.item = item
        }
    }
}