package com.technzone.bai3.ui.base.views

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.technzone.bai3.R


class AppTextInputEditText @JvmOverloads constructor(
    context: Context,
    private var attrs: AttributeSet,
    defStyleAttr: Int = R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

}