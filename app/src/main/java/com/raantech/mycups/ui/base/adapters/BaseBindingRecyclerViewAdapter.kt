package com.raantech.mycups.ui.base.adapters

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingRecyclerViewAdapter<MODEL>(
    val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<MODEL> = ArrayList()
    var itemClickListener: OnItemClickListener? = null



    fun submitItems(newItems: List<MODEL>) {
        if (items == newItems) {
            return
        }

        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun submitNewItems(list: List<MODEL>?) {
        items = ArrayList()
        list?.let {
            items.addAll(it)
            notifyDataSetChanged()
        }

    }
    fun submitItemsToTop(newItems: List<MODEL>) {
        items.addAll(0,newItems)
        notifyDataSetChanged()
    }

    fun addItems(list: List<MODEL>?) {
        val oldItems = items
        list?.let {
            items.addAll(it)
            notifyItemRangeChanged(oldItems.size, list.size)
        }

    }

    fun submitItem(newItem: MODEL, position: Int = 0) {
        items.add(position, newItem)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): MODEL {
        return items[position]
    }

    fun clear(){
        items=ArrayList()
        notifyDataSetChanged()
    }

    fun removeItemAt(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int, item: Any)
        fun onItemLongClick(view: View?, position: Int, item: Any){}
    }
}