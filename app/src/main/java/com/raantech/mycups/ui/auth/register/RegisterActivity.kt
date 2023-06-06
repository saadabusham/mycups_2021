package com.raantech.mycups.ui.auth.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import com.raantech.mycups.R
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.databinding.ActivityRegisterBinding
import com.raantech.mycups.ui.auth.AuthActivity
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseBindingActivity<ActivityRegisterBinding,Nothing>() {

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
            isActivityResult: Boolean,
            resultLauncher: ActivityResultLauncher<Intent>? = null
        ) {
            val intent = Intent(context, RegisterActivity::class.java)
            intent.putExtra(Constants.BundleData.IS_ACTIVITY_RESULT, isActivityResult)
            resultLauncher?.launch(intent)
        }
    }

}