package com.technzone.bai3.utils.extensions

import android.app.Activity
import com.technzone.bai3.R
import com.technzone.bai3.ui.base.dialogs.aleter.AlerterDialog

fun Activity?.showErrorAlert(
    title: String = "",
    message: String,
    titleColor: Int = R.color.error_color,
    lineColor: Int = R.color.error_color,
    timeToDismiss: Long = AlerterDialog.DEFAULT_TIME_TO_DISMISS

) {
//    Alerter.create(this)
//        .setTitle(title)
//        .setTitleAppearance(R.style.AlertTextAppearance)
//        .setText(message)
//        .setTextAppearance(R.style.AlertTextAppearance)
//        .setBackgroundColorRes(R.color.alert_red) // or setBackgroundColorInt(Color.CYAN)
//        .setLayoutGravity(Gravity.BOTTOM)
//        .show()
    this?.let {
        AlerterDialog(
            context = it,
            title = title,
            description = message,
            titleColor = titleColor,
            lineColor = lineColor,
            timeToDismiss = timeToDismiss
        ).show()
    }
}

//fun Activity.showUpdateDialog(updateStatus: UpdateStatus) {
//    UpdateAppDialog(this,updateStatus)
//        .show()
//}