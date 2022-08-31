package com.raantech.mycups.ui.base.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.raantech.mycups.R
import com.raantech.mycups.data.enums.OrderStatusEnum
import com.raantech.mycups.utils.extensions.gone

@BindingAdapter("textColorRes")
fun TextView.textColorRes(
    color: Int
) {
    setTextColor(context.getColor(color))
}

@BindingAdapter("text_selected")
fun TextView.setTextIsSelected(
    selected: Boolean
) {
    isSelected = selected
}

@BindingAdapter("order_text_by_status")
fun TextView.setOrderTextByStatus(
    status: Int
) {
    when (status) {
        OrderStatusEnum.WAITING_FOR_OFFER_PRICE.value -> {
            text = context.getString(R.string.waiting_for_price_quote)
        }
        OrderStatusEnum.OFFER_PRICE_RECEIVED.value -> {
            text = context.getString(R.string.a_price_offer_has_been_submitted)
        }
        else -> {
            gone()
        }
    }
}
