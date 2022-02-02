package com.technzone.bai3.ui.main.fragments.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technzone.bai3.data.enums.ProductTypesEnum
import com.technzone.bai3.data.models.home.product.productdetails.Product
import com.technzone.bai3.databinding.RowGeneralProductBinding
import com.technzone.bai3.databinding.RowHouseProductBinding
import com.technzone.bai3.databinding.RowMotorProductBinding
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.base.adapters.BaseViewHolder

class ProductsAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Product>(context) {
    override fun getItemViewType(position: Int): Int {
        return items[position].categoryType ?: ProductTypesEnum.GENERAL.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ProductTypesEnum.MOTORS.value -> {
                MotorsViewHolder(
                    RowMotorProductBinding.inflate(
                        LayoutInflater.from(context), parent, false
                    )
                )
            }
            ProductTypesEnum.HOUSE.value -> {
                HouseViewHolder(
                    RowHouseProductBinding.inflate(
                        LayoutInflater.from(context), parent, false
                    )
                )
            }
            else -> GeneralViewHolder(
                RowGeneralProductBinding.inflate(
                    LayoutInflater.from(context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GeneralViewHolder -> {
                holder.bind(items[position])
            }
            is MotorsViewHolder -> {
                holder.bind(items[position])
            }
            is HouseViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    inner class GeneralViewHolder(private val binding: RowGeneralProductBinding) :
        BaseViewHolder<Product>(binding.root) {

        override fun bind(item: Product) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }

    inner class MotorsViewHolder(private val binding: RowMotorProductBinding) :
        BaseViewHolder<Product>(binding.root) {

        override fun bind(item: Product) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }

    inner class HouseViewHolder(private val binding: RowHouseProductBinding) :
        BaseViewHolder<Product>(binding.root) {

        override fun bind(item: Product) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }


}