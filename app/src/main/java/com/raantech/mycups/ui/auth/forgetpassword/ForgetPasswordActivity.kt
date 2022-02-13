package com.raantech.mycups.ui.auth.forgetpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.raantech.mycups.R
import com.raantech.mycups.databinding.ActivityForgetPasswordBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordActivity : BaseBindingActivity<ActivityForgetPasswordBinding,Nothing>() {

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.nothing, R.anim.slide_in_bottom);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.nothing)
        setContentView(R.layout.activity_forget_password, hasToolbar = false)
    }

    companion object {
        fun start(
                context: Context?
        ) {
            val intent = Intent(context, ForgetPasswordActivity::class.java)
            context?.startActivity(intent)
        }
    }


}