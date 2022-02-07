package com.technzone.bai3.ui.auth.forgetpassword.fragments

import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.models.auth.login.UserDetailsResponseModel
import com.technzone.bai3.databinding.FragmentVerificationForgetPasswordBinding
import com.technzone.bai3.ui.auth.forgetpassword.presenters.VerificationForgetPasswordPresenter
import com.technzone.bai3.ui.auth.forgetpassword.viewmodels.ForgetPasswordViewModel
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.utils.extensions.showErrorAlert
import com.technzone.bai3.utils.extensions.validate
import com.technzone.bai3.utils.validation.ValidatorInputTypesEnums
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