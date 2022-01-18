package com.technzone.bai3.ui.base.bindingadapters

import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.technzone.bai3.R


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