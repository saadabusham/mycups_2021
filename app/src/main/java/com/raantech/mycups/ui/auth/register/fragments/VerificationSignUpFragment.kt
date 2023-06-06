package com.raantech.mycups.ui.auth.register.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.auth.login.TokenModel
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.databinding.FragmentVerificationRegisterBinding
import com.raantech.mycups.ui.auth.register.presenter.VerificationRegisterPresenter
import com.raantech.mycups.ui.auth.register.viewmodels.RegistrationViewModel
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.validate
import com.raantech.mycups.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_verification_register.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class VerificationSignUpFragment : BaseBindingFragment<FragmentVerificationRegisterBinding, VerificationRegisterPresenter>(),VerificationRegisterPresenter {

    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_verification_register
    override fun getPresenter(): VerificationRegisterPresenter = this

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.activate_account
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
                        if (requireActivity().intent.getBooleanExtra(
                                Constants.BundleData.IS_ACTIVITY_RESULT,
                                false
                            )
                        ) {
                            requireActivity().setResult(Activity.RESULT_OK, Intent().apply {
                                this.putExtra(Constants.BundleData.IS_LOGIN_SUCCESS, true)
                            })
                            requireActivity().finish()
                        } else {
                            MainActivity.start(context)
                        }
                    }
                }
            })
    }

    private fun sendOtpResultObserver(): CustomObserverResponse<TokenModel> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<TokenModel> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: TokenModel?
                ) {
                    viewModel.tokenMutableLiveData.value = data?.token
                    viewModel.startHandleResendSignUpPinCodeTimer()
                }
            })
    }

    private fun setUpViewsListeners() {
        otp_view.setAnimationEnable(true)
    }

    override fun onResendClicked() {
        if (viewModel.signUpResendPinCodeEnabled.value == true) {
            viewModel.resendVerificationCode().observe(this, sendOtpResultObserver())
        }
    }

    override fun onVerifyClicked() {
        if (validateInput()) {
            viewModel.verifyCode().observe(this, verifyOtpResultObserver())
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