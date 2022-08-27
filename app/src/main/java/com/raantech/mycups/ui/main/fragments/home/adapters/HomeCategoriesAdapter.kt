package com.raantech.mycups.ui.main.fragments.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.databinding.RowHomeCategoriesBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener

class HomeCategoriesAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Category>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowHomeCategoriesBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowHomeCategoriesBinding) :
        BaseViewHolder<Category>(binding.root) {
        var adapter: HomeItemsAdapter? = null
        override fun bind(item: Category) {
            binding.item = item
            if (adapter == null && !item.items.isNullOrEmpty()) {
                adapter = HomeItemsAdapter(context)
                binding.rvData.adapter = adapter
                binding.rvData.setOnItemClickListener(itemClickListener)
                adapter?.submitNewItems(item.items)
            }
        }
    }


}