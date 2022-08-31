package com.raantech.mycups.ui.more.orders.fragments.orders.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.orders.Order
import com.raantech.mycups.databinding.RowOrderBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class OrdersRecyclerAdapter constructor(
    context: Context
) : BaseBindingRecyclerViewAdapter<Order>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowOrderBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowOrderBinding) :
        BaseViewHolder<Order>(binding.root) {

        override fun bind(item: Order) {
            binding.item = item
            binding.lifecycleOwner = context as AppCompatActivity
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }
}