package com.raantech.mycups.ui.base.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("textColorRes")
fun TextView.textColorRes(
    color: Int
) {
    setTextColor(context.getColor(color))
}