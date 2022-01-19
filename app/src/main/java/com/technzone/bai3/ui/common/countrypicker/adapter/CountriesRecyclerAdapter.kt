package com.technzone.bai3.ui.common.countrypicker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technzone.bai3.R
import com.technzone.bai3.data.models.general.Countries
import com.technzone.bai3.databinding.RowCountryBinding
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.base.adapters.BaseViewHolder
import com.technzone.bai3.utils.extensions.gone
import com.technzone.bai3.utils.extensions.visible

class CountriesRecyclerAdapter(
    context: Context,
    private val showCode:Boolean
) : BaseBindingRecyclerViewAdapter<Countries>(context) {

    fun getSelectedItem(): Countries? {
        return items.singleOrNull { it.selected }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowCountryBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowCountryBinding) :
        BaseViewHolder<Countries>(binding.root) {

        override fun bind(item: Countries) {
            binding.item = item
            binding.showCode = showCode
            binding.root.setOnClickListener {
                items.withIndex().singleOrNull { it.value.selected }?.let {
                    it.value.selected = false
                    notifyItemChanged(it.index)
                }
                item.selected = true
                notifyItemChanged(bindingAdapterPosition)
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
            if (item.selected) {
                binding.imgSelected.visible()
                binding.relativeContainer.setBackgroundColor(context.resources.getColor(R.color.app_light_orange))
            } else {
                binding.imgSelected.gone()
                binding.relativeContainer.setBackgroundColor(context.resources.getColor(R.color.white))
            }
        }
    }
}