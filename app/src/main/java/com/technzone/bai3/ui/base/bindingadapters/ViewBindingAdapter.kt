package com.technzone.bai3.ui.base.bindingadapters

import android.graphics.Color
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("vHexBackground")
fun View?.setHexBackgroundColor(color: String) {
    this?.setBackgroundColor(Color.parseColor(color))
}