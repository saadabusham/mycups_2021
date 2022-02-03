package com.technzone.bai3.ui.base.views.appviews

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import com.technzone.bai3.R


class AppEditText @JvmOverloads constructor(
    context: Context,
    private var attrs: AttributeSet,
    defStyleAttr: Int = R.attr.editTextStyle
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        initAttrs()
    }
    private fun initAttrs() {
        if (gravity != Gravity.CENTER) {
            textAlignment = TEXT_ALIGNMENT_VIEW_START
        }
    }

}