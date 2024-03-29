package com.raantech.mycups.ui.offerdetails.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.orders.MeasurementOffer
import com.raantech.mycups.databinding.RowOfferProductBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class OfferDetailsRecyclerAdapter constructor(
    context: Context,
    private val product: Product
) : BaseBindingRecyclerViewAdapter<MeasurementOffer>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowOfferProductBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowOfferProductBinding) :
        BaseViewHolder<MeasurementOffer>(binding.root) {

        override fun bind(item: MeasurementOffer) {
            binding.item = product
            binding.offer = item
        }
    }
}