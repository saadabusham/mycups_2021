package com.technzone.baseapp.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.technzone.baseapp.R
import com.technzone.baseapp.common.MyApplication
import com.technzone.baseapp.data.api.response.APIResource
import com.technzone.baseapp.data.api.response.RequestStatusEnum
import com.technzone.baseapp.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.baseapp.data.common.CustomObserverResponse
import com.technzone.baseapp.data.models.auth.login.UserDetailsResponseModel
import com.technzone.baseapp.data.models.configuration.ConfigurationWrapperResponse
import com.technzone.baseapp.databinding.ActivitySplashBinding
import com.technzone.baseapp.ui.MainActivity
import com.technzone.baseapp.ui.auth.AuthActivity
import com.technzone.baseapp.ui.base.activity.BaseBindingActivity
import com.technzone.baseapp.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseBindingActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModels { defaultViewModelProviderFactory }

    @Inject
    lateinit var myApp: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_splash,
            hasToolbar = false
        )
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getConfigurationData().observe(this, configurationResultObserver())
        }, 3000)

        RuntimeException("This is a RUNTIME EXCEPTION")
    }

    private fun configurationResultObserver(): CustomObserverResponse<ConfigurationWrapperResponse> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<ConfigurationWrapperResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ConfigurationWrapperResponse?
                ) {
                    SharedPreferencesUtil.getInstance(this@SplashActivity)
                        .setConfigurationPreferences(data)
                    goToNextPage()
                }
            })
    }

    private fun tokenObserver(): CustomObserverResponse<UserDetailsResponseModel> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<UserDetailsResponseModel> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: UserDetailsResponseModel?
                ) {
                    data?.let { it1 -> viewModel.storeUser(it1) }
                    MainActivity.start(this@SplashActivity)
                }

                override fun onError(subErrorCode: ResponseSubErrorsCodeEnum, message: String) {
                    viewModel.logout()
                    AuthActivity.start(this@SplashActivity)
                }
            }
        )

    }

    private fun goToNextPage() {
        if (!viewModel.isUserLoggedIn()) {
            AuthActivity.start(this)
        } else {
            viewModel.updateAccessToken().observe(this, tokenObserver())
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onNewIntent(intent: Intent?) {
        this.intent = intent
        super.onNewIntent(intent)
    }


    companion object {

        fun start(context: Context?) {
            val intent = Intent(context, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        }

    }

}