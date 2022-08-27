package com.raantech.mycups.ui.productdetails.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.home.product.productdetails.Measurement
import com.raantech.mycups.databinding.RowHomeCategoriesBinding
import com.raantech.mycups.databinding.RowMeasurementBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener

class MeasurementAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Measurement>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowMeasurementBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowMeasurementBinding) :
        BaseViewHolder<Measurement>(binding.root) {
        override fun bind(item: Measurement) {
            binding.item = item
            binding.lifecycleOwner = context as AppCompatActivity
            binding.imgMinus.setOnClickListener {
                if (item.count.value == 0)
                    return@setOnClickListener
                item.count.value = (item.count.value ?: 1) - 1
            }
            binding.imgPlus.setOnClickListener {
                item.count.value = (item.count.value ?: 1) + 1
            }
        }
    }


}