package com.raantech.mycups.ui.base.bindingadapters

import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.raantech.mycups.R


@BindingAdapter("btnEnabledClick")
fun Button?.setIsEnabledClick(isEnable: Boolean) {
    if(isEnable){
        this?.backgroundTintList =
            this?.context?.let { ContextCompat.getColorStateList(it, R.color.button_color) }
        this?.setTextColor(ContextCompat.getColor(this.context, R.color.white))
    }else{
        this?.backgroundTintList =
            this?.context?.let { ContextCompat.getColorStateList(it, R.color.button_color_disabled) }
        this?.setTextColor(ContextCompat.getColor(this.context, R.color.text_dark_color));
    }
}