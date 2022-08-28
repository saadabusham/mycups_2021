package com.raantech.mycups.ui.more.aboutus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.more.AboutUsResponse
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.utils.extensions.openDial
import com.raantech.mycups.utils.extensions.openEmail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_about_us.*

@AndroidEntryPoint
class AboutUsActivity :
    BaseBindingActivity<com.raantech.mycups.databinding.ActivityAboutUsBinding, Nothing>() {

    private val viewModel: AboutUsViewModel by viewModels()
    var aboutUsResponse: AboutUsResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_about_us,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = resources.getString(R.string.menu_about_us)

        )
        viewModel.getAboutUs().observe(this, aboutUsResultObserver())
        setUpListeners()
    }

    private fun setUpListeners() {
        imgCall?.setOnClickListener { openDial(aboutUsResponse?.app_phone) }
        imgEmail?.setOnClickListener { openEmail(aboutUsResponse?.app_email) }
    }

    private fun aboutUsResultObserver(): CustomObserverResponse<AboutUsResponse> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<AboutUsResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: AboutUsResponse?
                ) {
                    aboutUsResponse = data
                    binding?.body = data?.about_us
                }
            })
    }


    companion object {

        fun start(context: Context?) {
            val intent = Intent(context, AboutUsActivity::class.java)
            context?.startActivity(intent)
        }

    }

}