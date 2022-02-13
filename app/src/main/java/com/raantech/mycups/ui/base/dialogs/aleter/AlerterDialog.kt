package com.raantech.mycups.ui.base.dialogs.aleter

import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.databinding.DataBindingUtil
import com.raantech.mycups.R
import com.raantech.mycups.databinding.DialogAlerterBinding

class AlerterDialog(
    val context: Activity,
    val title: String = context.resources.getString(R.string.alert_dialog_title),
    val description: String = context.resources.getString(R.string.completed_successfully),
    val titleColor: Int = R.color.error_color,
    val lineColor: Int = R.color.error_color,
    val timeToDismiss: Long = DEFAULT_TIME_TO_DISMISS
) : Dialog(context) {

    private lateinit var binding: DialogAlerterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(0))
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setGravity(Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.DialogAnimation)
        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_alerter,
                null,
                false
            )
        binding.dialog = this
        setContentView(binding.root)
        window?.setLayout(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        
        setCancelable(false)
        setUpListeners()
        dismissTimer.start()
    }

    private fun setUpListeners() {
        binding.imgClose.setOnClickListener {
            dismissTimer.cancel()
            dismiss()
        }
    }

    private val dismissTimer = object : CountDownTimer(timeToDismiss, timeToDismiss) {
        override fun onFinish() {
            dismiss()
        }

        override fun onTick(millisUntilFinished: Long) {
        }

    }

    companion object {
        const val DEFAULT_TIME_TO_DISMISS: Long = 3000
    }
}
