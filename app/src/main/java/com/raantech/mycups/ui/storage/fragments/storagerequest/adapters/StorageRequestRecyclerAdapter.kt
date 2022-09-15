package com.raantech.mycups.ui.storage.fragments.storagerequest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.storage.Storage
import com.raantech.mycups.databinding.RowOfferProductBinding
import com.raantech.mycups.databinding.RowStorageBinding
import com.raantech.mycups.databinding.RowStorageRequestBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class StorageRequestRecyclerAdapter constructor(
    context: Context
) : BaseBindingRecyclerViewAdapter<Storage>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowStorageRequestBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowStorageRequestBinding) :
        BaseViewHolder<Storage>(binding.root) {

        override fun bind(item: Storage) {
            binding.item = item
            binding.lifecycleOwner = context as AppCompatActivity
            binding.imgMinus.setOnClickListener {
                if (item.count.value == 0)
                    return@setOnClickListener
                item.count.value = (item.count.value ?: 1) - 1
            }
            binding.imgPlus.setOnClickListener {
                if ((item.count.value ?: 0) >= (item.quantity ?: 0))
                    return@setOnClickListener
                item.count.value = (item.count.value ?: 1) + 1
            }
        }
    }
}