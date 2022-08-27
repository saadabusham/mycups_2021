package com.raantech.mycups.ui.notifications.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.data.models.notification.Notification
import com.raantech.mycups.databinding.RowNotificationBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.adapters.BaseViewHolder

class NotificationsRecyclerAdapter constructor(
        context: Context
) : BaseBindingRecyclerViewAdapter<Notification>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
                RowNotificationBinding.inflate(
                        LayoutInflater.from(context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: RowNotificationBinding) :
            BaseViewHolder<Notification>(binding.root) {

        override fun bind(item: Notification) {
            binding.item = item
        }
    }
}