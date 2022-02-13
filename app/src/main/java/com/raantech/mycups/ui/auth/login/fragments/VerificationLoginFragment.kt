package com.raantech.mycups.ui.auth.login.fragments

import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.pref.user.UserPref
import com.raantech.mycups.databinding.FragmentVerificationLoginBinding
import com.raantech.mycups.ui.auth.login.presenter.VerificationLoginPresenter
import com.raantech.mycups.ui.auth.login.viewmodels.LoginViewModel
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_verification_login.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

@AndroidEntryPoint
class VerificationLoginFragment :
    BaseBindingFragment<FragmentVerificationLoginBinding, VerificationLoginPresenter>(),
    VerificationLoginPresenter {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_verification_login

    override fun getPresenter(): VerificationLoginPresenter = this

    @Inject
    lateinit var prefs: UserPref

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.empty_string
        )
        setUpViewsListeners()
        setUpData()
    }

    private fun setUpData() {
        binding?.viewModel = viewModel
//        viewModel.resendVerificationCode().observe(this, sendOtpResultObserver())

        viewModel.startHandleResendSignUpPinCodeTimer()
    }

    private fun verifyOtpResultObserver(): CustomObserverResponse<UserDetailsResponseModel> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<UserDetailsResponseModel> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: UserDetailsResponseModel?
                ) {
                    data?.let {
                        viewModel.storeUser(it)
                        navigationController.navigate(R.id.action_verificationLoginFragment_to_phoneVerifiedFragment)
                    }
                }
            })
    }

    private fun sendOtpResultObserver(): CustomObserverResponse<String> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<String> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: String?
                ) {
                    viewModel.userIdMutableLiveData.postValue(data)
                    viewModel.startHandleResendSignUpPinCodeTimer()
                }
            })
    }

    override fun onVerifyClicked() {

    }

    override fun onResendClicked() {
        if (viewModel.signUpResendPinCodeEnabled.value == true) {
            viewModel.resendVerificationCode().observe(this, sendOtpResultObserver())
        }
    }

    private fun setUpViewsListeners() {
        otp_view.setAnimationEnable(true)
        binding?.otpView?.setOtpCompletionListener {
            viewModel.verifyCode().observe(this, verifyOtpResultObserver())
        }
    }

}