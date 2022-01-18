package com.technzone.bai3.ui.auth.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.technzone.bai3.R
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.databinding.ActivityRegisterBinding
import com.technzone.bai3.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseBindingActivity<ActivityRegisterBinding>() {

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.nothing, R.anim.slide_in_bottom);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.nothing)
        setContentView(R.layout.activity_register, hasToolbar = false)
    }

    companion object {
        const val REQUEST_CODE = 1
        fun start(
            context: Context?
        ) {
            val intent = Intent(context, RegisterActivity::class.java)
            context?.startActivity(intent)
        }

        fun startForResult(
            context: Activity?,
            isActivityResult: Boolean
        ) {
            val intent = Intent(context, RegisterActivity::class.java)
            intent.putExtra(Constants.BundleData.IS_ACTIVITY_RESULT, isActivityResult)
            context?.startActivityForResult(intent, REQUEST_CODE)
        }
    }

}