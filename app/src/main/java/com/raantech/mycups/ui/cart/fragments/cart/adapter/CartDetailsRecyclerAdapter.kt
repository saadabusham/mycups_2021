package com.raantech.mycups.ui.cart.fragments.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.databinding.RowCartProductBinding
import com.raantech.mycups.databinding.RowOfferProductBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class CartDetailsRecyclerAdapter constructor(
    context: Context
) : BaseBindingRecyclerViewAdapter<Product>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowCartProductBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowCartProductBinding) :
        BaseViewHolder<Product>(binding.root) {

        override fun bind(item: Product) {
            binding.item = item
        }
    }
}