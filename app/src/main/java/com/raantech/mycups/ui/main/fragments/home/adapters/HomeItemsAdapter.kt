package com.raantech.mycups.ui.main.fragments.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.offer.Offer
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.databinding.*
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class HomeItemsAdapter(
    context: Context
) : BaseBindingRecyclerViewAdapter<Any>(context) {
    companion object {
        const val OFFERS = 1
        const val CATEGORIES = 2
        const val DESIGN_CATEGORIES = 3
        const val PRODUCTS = 4
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Offer -> OFFERS
            is CategoriesItem -> CATEGORIES
            is DesignCategory -> DESIGN_CATEGORIES
            else -> PRODUCTS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            OFFERS -> {
                OffersViewHolder(
                    RowOffersBinding.inflate(
                        LayoutInflater.from(context), parent, false
                    )
                )
            }
            CATEGORIES -> {
                CategoriesViewHolder(
                    RowCategoryBinding.inflate(
                        LayoutInflater.from(context), parent, false
                    )
                )
            }
            DESIGN_CATEGORIES -> {
                DesignCategoriesViewHolder(
                    RowDesignsCategoryBinding.inflate(
                        LayoutInflater.from(context), parent, false
                    )
                )
            }
            else -> ProductsViewHolder(
                RowProductHorizontalBinding.inflate(
                    LayoutInflater.from(context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductsViewHolder -> {
                holder.bind(items[position] as Product)
            }
            is OffersViewHolder -> {
                holder.bind(items[position] as Offer)
            }
            is CategoriesViewHolder -> {
                holder.bind(items[position] as CategoriesItem)
            }
            is DesignCategoriesViewHolder -> {
                holder.bind(items[position] as DesignCategory)
            }
        }
    }

    inner class OffersViewHolder(private val binding: RowOffersBinding) :
        BaseViewHolder<Offer>(binding.root) {

        override fun bind(item: Offer) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }

    inner class CategoriesViewHolder(private val binding: RowCategoryBinding) :
        BaseViewHolder<CategoriesItem>(binding.root) {

        override fun bind(item: CategoriesItem) {
            binding.item = item
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }

    inner class DesignCategoriesViewHolder(private val binding: RowDesignsCategoryBinding) :
        BaseViewHolder<DesignCategory>(binding.root) {

        override fun bind(item: DesignCategory) {
            binding.item = item
            binding.lifecycleOwner = context as AppCompatActivity
            binding.root.setOnClickListener {
                if (item.canSelect == true) {
                    items.withIndex().singleOrNull {
                        (it.value as DesignCategory).isSelected.value == true
                    }?.let {
                        if ((it.value as DesignCategory).id != item.id) {
                            (it.value as DesignCategory).isSelected.value = false
                        }
                    }
                    item.isSelected.value = item.isSelected.value == false
                }
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }


    inner class ProductsViewHolder(private val binding: RowProductHorizontalBinding) :
        BaseViewHolder<Product>(binding.root) {

        override fun bind(item: Product) {
            binding.item = item
            binding.cvImage.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition, item)
            }
        }
    }


}