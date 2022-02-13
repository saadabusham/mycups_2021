package com.raantech.mycups.ui.more.settings.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.general.GeneralLookup
import com.raantech.mycups.databinding.RowGeneralSelectionBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class LanguageRecyclerAdapter constructor(
    context: Context
) : BaseBindingRecyclerViewAdapter<GeneralLookup>(context) {

    fun getSelectedItem(): GeneralLookup?{
        return items.singleOrNull { it.isSelected.value == true }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NormalViewHolder(
            RowGeneralSelectionBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class NormalViewHolder(private val binding: RowGeneralSelectionBinding) :
        BaseViewHolder<GeneralLookup>(binding.root) {
        override fun bind(item: GeneralLookup) {
            binding.item = item
            binding.lifecycleOwner = context as AppCompatActivity
            binding.root.setOnClickListener {
                items.withIndex().singleOrNull{it.value.isSelected.value == true}?.let {
                    it.value.isSelected.value = false
                }
                item.isSelected.value = true
                itemClickListener?.onItemClick(binding.root, bindingAdapterPosition, item)
            }
        }
    }
}