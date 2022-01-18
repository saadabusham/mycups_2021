package com.technzone.bai3.ui.base.bindingadapters

import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import com.technzone.bai3.R
import com.technzone.bai3.data.enums.InputFieldValidStateEnums

@BindingAdapter("etOnEditorActionListener")
fun EditText?.setOnEditorActionListener(
    listener: OnOkInSoftKeyboardListener
) {
    this?.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
        listener.onOkInSoftKeyboard(actionId)
        false
    }
}

@BindingAdapter(
    value = ["etChangeTVFocus", "etChangeTVFocusColor", "etChangeTVNotFocusColor"],
    requireAll = false
)
fun EditText?.setOnFocustChangeTitleFocus(
    @IdRes tvId: Int,
    @ColorRes focusColor: Int? = null,
    @ColorRes notFocusColor: Int? = null
) {

    val textView: TextView? = if ((this?.parentForAccessibility is View)) {
        (this.parentForAccessibility as View).findViewById(tvId)
    } else {
        (this?.parent as View).findViewById(tvId)
    }


    this.setOnFocusChangeListener { _, isFocus ->
        if (isFocus) {
            textView?.setTextColor(resources.getColor(focusColor ?: R.color.text_dark_color))
        } else {
            textView?.setTextColor(resources.getColor(notFocusColor ?: R.color.text_gray_color))
        }
    }
}

fun TextView.updateStrokeColor(inputFieldValidStateEnums: InputFieldValidStateEnums) {
    when(inputFieldValidStateEnums){
        InputFieldValidStateEnums.VALID -> {
            this.background = context.resources.getDrawable(R.drawable.shape_edittext)
        }
        InputFieldValidStateEnums.ERROR -> {
            this.background = context.resources.getDrawable(R.drawable.shape_edittext_error)
        }
    }
}

fun TextView.updateStrokeLightColor(inputFieldValidStateEnums: InputFieldValidStateEnums) {
    when(inputFieldValidStateEnums){
        InputFieldValidStateEnums.VALID -> {
            this.background = context.resources.getDrawable(R.drawable.shape_edittext_not_focused)
        }
        InputFieldValidStateEnums.ERROR -> {
            this.background = context.resources.getDrawable(R.drawable.shape_edittext_error_view)
        }
    }
}

interface OnOkInSoftKeyboardListener {
    fun onOkInSoftKeyboard(actionId: Int)
}