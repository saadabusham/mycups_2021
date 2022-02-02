package com.technzone.bai3.ui.main.fragments.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technzone.bai3.data.models.category.Category
import com.technzone.bai3.databinding.RowCategoriesItemsBinding
import com.technzone.bai3.databinding.RowCategoryBinding
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.base.adapters.BaseViewHolder
import com.technzone.bai3.ui.base.bindingadapters.setOnItemClickListener

class CategoriesItemsAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Category>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowCategoriesItemsBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowCategoriesItemsBinding) :
        BaseViewHolder<Category>(binding.root) {
        var adapter : ProductsAdapter?=null
        override fun bind(item: Category) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
            if(adapter == null && !item.products.isNullOrEmpty()){
                binding.rvData.adapter = adapter
                binding.rvData.setOnItemClickListener(itemClickListener)
                adapter?.submitNewItems(item.products)
            }
        }
    }


}