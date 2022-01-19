package com.technzone.bai3.ui.auth.onboarding.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technzone.bai3.R
import com.technzone.bai3.databinding.RowIndecatorBinding
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.base.adapters.BaseViewHolder

class IndecatorRecyclerAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Boolean>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowIndecatorBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowIndecatorBinding) :
        BaseViewHolder<Boolean>(binding.root) {

        override fun bind(item: Boolean) {
            if (item) {
                binding.imgDotImage.setCardBackgroundColor(context.resources.getColor(R.color.indicator_active))
            } else {
                binding.imgDotImage.setCardBackgroundColor(context.resources.getColor(R.color.indicator_desactive))
            }
        }
    }
}