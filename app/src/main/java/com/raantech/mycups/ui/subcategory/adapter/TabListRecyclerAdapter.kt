package com.raantech.mycups.ui.subcategory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.databinding.RowTabBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

class TabListRecyclerAdapter constructor(
    context: Context
) : BaseBindingRecyclerViewAdapter<CategoriesItem>(context) {

    fun getSelectedItem(): CategoriesItem? {
        return items.singleOrNull { it.isSelected.value ?: false }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            RowTabBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowTabBinding) :
        BaseViewHolder<CategoriesItem>(binding.root) {

        override fun bind(item: CategoriesItem) {
            binding.data = item
            binding.lifecycleOwner = context as AppCompatActivity
            binding.root.setOnClickListener {
                items.withIndex()
                    .singleOrNull { (i, value) -> value.isSelected.value!! }
                    ?.let {
                        if (bindingAdapterPosition == it.index)
                            return@let
                        it.value.isSelected.value = (false)
                    }
                item.isSelected.value = (true)
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }
}