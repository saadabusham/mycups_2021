package com.technzone.bai3.ui.auth.register.fragments

import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.models.auth.login.UserDetailsResponseModel
import com.technzone.bai3.databinding.FragmentVerificationRegisterBinding
import com.technzone.bai3.ui.auth.register.viewmodels.RegistrationViewModel
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.main.activity.MainActivity
import com.technzone.bai3.utils.extensions.showErrorAlert
import com.technzone.bai3.utils.extensions.validate
import com.technzone.bai3.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_verification_register.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class VerificationSignUpFragment : BaseBindingFragment<FragmentVerificationRegisterBinding>() {

    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_verification_register

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
                        MainActivity.start(requireContext())
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
                    viewModel.startHandleResendSignUpPinCodeTimer()
                }
            })
    }

    private fun setUpViewsListeners() {
        otp_view.setAnimationEnable(true)
        tvTimeToResend?.setOnClickListener {
            if (viewModel.signUpResendPinCodeEnabled.value == true) {
                viewModel.resendVerificationCode().observe(this, sendOtpResultObserver())
            }
        }
        binding?.btnConfirm?.setOnClickListener {
            if (validateInput()) {
                viewModel.verifyCode().observe(this, verifyOtpResultObserver())
            }
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