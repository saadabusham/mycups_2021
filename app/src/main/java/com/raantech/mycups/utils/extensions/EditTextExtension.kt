package com.raantech.mycups.utils.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.raantech.mycups.R

@SuppressLint("ClickableViewAccessibility")
fun EditText.setupClearButtonWithAction() {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val clearIcon = if (editable?.isNotEmpty() == true) R.drawable.ic_close_keyboard else 0
            setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    })
    setOnTouchListener(View.OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                this.setText("")
                hideKeyboard(this.context)
                this.clearFocus()
                return@OnTouchListener true
            }
        }
        return@OnTouchListener false
    })
}

fun EditText.focus() {
    this.requestFocus()
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
}
fun EditText.addEndDrawable(icon:Int) {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
}
fun EditText.removeEndDrawable() {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
}