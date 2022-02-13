package com.raantech.mycups.ui.more.faqs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.FaqsResponse
import com.raantech.mycups.databinding.RowFaqBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.rotate
import com.raantech.mycups.utils.extensions.visible

class FaqsRecyclerAdapter constructor(
    context: Context
) : BaseBindingRecyclerViewAdapter<FaqsResponse>(context) {

    var lastPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowFaqBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowFaqBinding) :
        BaseViewHolder<FaqsResponse>(binding.root) {

        override fun bind(item: FaqsResponse) {
            binding.root.setOnClickListener {
                if (!item.isExpanded) {
                    binding.tvDesc.maxLines = 1000
                    binding.ivArrow.rotate(true)
                    binding.tvDesc.visible()
                } else {
                    binding.tvDesc.maxLines = 1
                    binding.ivArrow.rotate(false)
                    binding.tvDesc.gone()
                }
                item.isExpanded = !item.isExpanded
            }
            binding.tvTitle.text = item.question
            binding.tvDesc.text = item.answer
        }
    }
}