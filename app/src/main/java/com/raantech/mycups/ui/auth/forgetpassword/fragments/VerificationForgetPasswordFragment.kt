package com.raantech.mycups.ui.auth.forgetpassword.fragments

import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.databinding.FragmentVerificationForgetPasswordBinding
import com.raantech.mycups.ui.auth.forgetpassword.presenters.VerificationForgetPasswordPresenter
import com.raantech.mycups.ui.auth.forgetpassword.viewmodels.ForgetPasswordViewModel
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.validate
import com.raantech.mycups.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_verification_forget_password.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class VerificationForgetPasswordFragment :
    BaseBindingFragment<FragmentVerificationForgetPasswordBinding, VerificationForgetPasswordPresenter>(),
    VerificationForgetPasswordPresenter {

    private val viewModel: ForgetPasswordViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_verification_forget_password
    override fun getPresenter(): VerificationForgetPasswordPresenter = this
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
        otp_view.setAnimationEnable(true)
        setUpData()
    }

    private fun setUpData() {
        binding?.viewModel = viewModel
        viewModel.startHandleResendSignUpPinCodeTimer()
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
                        navigationController.navigate(R.id.action_verificationForgetPassword_to_recoveryPasswordFragment)
                    }
                }
            })
    }

    override fun onVerifyClicked() {
        if (validateInput()) {
            viewModel.verifyCode().observe(this, verifyOtpResultObserver())
        }
    }

    override fun onResendClicked() {
        if (viewModel.signUpResendPinCodeEnabled.value == true) {
            viewModel.resendVerificationCode().observe(this, sendOtpResultObserver())
        }
    }

    private fun validateInput(): Boolean {
        otp_view.text.toString().validate(ValidatorInputTypesEnums.OTP, requireContext()).let {
            if (!it.isValid) {
                activity.showErrorAlert(it.errorTitle, it.errorMessage)
                return false
            }
        }
        return true
    }

}