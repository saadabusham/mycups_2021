package com.raantech.mycups.ui.base.views.appviews

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class AppImageViewClick @JvmOverloads constructor(
    context: Context,
    private var attrs: AttributeSet,
    private val defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    // global variables
    var clickHandler: Handler = Handler()
    var runnable: Runnable? = null
    var lastClickTime: Long = 0

    init {
        initAttrs()
        setUp()
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//
//        return if (event?.action != MotionEvent.ACTION_UP || isValid)
//            super.onTouchEvent(event)
//        else {
//            event.action = MotionEvent.ACTION_CANCEL
//            super.onTouchEvent(event)
//        }
//    }
//
//    private val isValid: Boolean
//        get() {
//            return if (lastClickTime == 0.toLong() || SystemClock.elapsedRealtime() - lastClickTime > 1000L) {
//                lastClickTime = SystemClock.elapsedRealtime()
//                true
//            } else false
//        }

    private fun initAttrs() {

    }

    private fun setUp() {
        this.setOnLongClickListener {
            if(!isEnabled || !isClickable){
                return@setOnLongClickListener true
            }
            runnable = Runnable {
                if (!this.isPressed) return@Runnable
                callOnClick()
                runnable?.let {
                    clickHandler.postDelayed(it, 100)
                }
            }
            runnable?.let {
                clickHandler.postDelayed(it, 100)
            }
            return@setOnLongClickListener true
        }
    }
}