package com.technzone.bai3.ui.more.settings.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.CommonEnums
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.databinding.ActivitySettingsBinding
import com.technzone.bai3.ui.base.activity.BaseBindingActivity
import com.technzone.bai3.ui.more.settings.dialogs.LanguageBottomSheet
import com.technzone.bai3.ui.more.settings.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class SettingsActivity : BaseBindingActivity<ActivitySettingsBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_settings,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = resources.getString(R.string.more_settings)

        )
        binding?.viewModel = viewModel
        setUpListeners()
    }

    private fun setUpListeners() {
        binding?.switchNotifications?.setOnCheckedChangeListener { buttonView, isChecked ->
//            viewModel.updateNotificationStatus(isChecked)
//                .observe(this, notificationStatusObserver(isChecked))
            viewModel.setIsNotificationsIsEnabled(isChecked)
        }
        linearLanguage?.setOnClickListener {
            showLanguageDialog()
        }
    }

    private fun showLanguageDialog() {
        LanguageBottomSheet(object : LanguageBottomSheet.LanguageCallBack {
            override fun callBack(englishSelected: Boolean) {
                viewModel.saveLanguage(englishSelected).observe(this@SettingsActivity, {
                    (this@SettingsActivity as BaseBindingActivity<*>)
                        .setLanguage(
                            if (englishSelected)
                                CommonEnums.Languages.English.value
                            else CommonEnums.Languages.Arabic.value
                        )
                })
            }
        }).show(supportFragmentManager, "languageSheet")
    }

    private fun notificationStatusObserver(enabled: Boolean): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    viewModel.storeUser(enabled)
                }
            },showError = false,withProgress = false
        )

    }

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, SettingsActivity::class.java)
            context?.startActivity(intent)
        }
    }

}