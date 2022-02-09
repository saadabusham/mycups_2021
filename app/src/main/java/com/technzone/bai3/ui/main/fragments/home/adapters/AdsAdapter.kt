package com.technzone.bai3.ui.main.fragments.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technzone.bai3.data.enums.AdsTypesEnum
import com.technzone.bai3.data.models.home.product.productdetails.Ads
import com.technzone.bai3.databinding.*
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.base.adapters.BaseViewHolder

class AdsAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Ads>(context) {
    override fun getItemViewType(position: Int): Int {
        return items[position].categoryType ?: AdsTypesEnum.GENERAL.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdsTypesEnum.MOTORS.value -> {
                MotorsViewHolder(
                    RowMotorAdsBinding.inflate(
                        LayoutInflater.from(context), parent, false
                    )
                )
            }
            AdsTypesEnum.HOUSE.value -> {
                HouseViewHolder(
                    RowHouseAdsBinding.inflate(
                        LayoutInflater.from(context), parent, false
                    )
                )
            }
            else -> GeneralViewHolder(
                RowGeneralAdsBinding.inflate(
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

    inner class GeneralViewHolder(private val binding: RowGeneralAdsBinding) :
        BaseViewHolder<Ads>(binding.root) {

        override fun bind(item: Ads) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }

    inner class MotorsViewHolder(private val binding: RowMotorAdsBinding) :
        BaseViewHolder<Ads>(binding.root) {

        override fun bind(item: Ads) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }

    inner class HouseViewHolder(private val binding: RowHouseAdsBinding) :
        BaseViewHolder<Ads>(binding.root) {

        override fun bind(item: Ads) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }


}