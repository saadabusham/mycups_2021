package com.technzone.bai3.ui.auth.forgetpassword.fragments

import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.TextTypingCallback
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.enums.InputFieldValidStateEnums
import com.technzone.bai3.databinding.FragmentForgetPasswordBinding
import com.technzone.bai3.ui.auth.forgetpassword.presenters.ForgetPasswordPresenter
import com.technzone.bai3.ui.auth.forgetpassword.viewmodels.ForgetPasswordViewModel
import com.technzone.bai3.ui.base.bindingadapters.updateStrokeColor
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.utils.extensions.gone
import com.technzone.bai3.utils.extensions.validate
import com.technzone.bai3.utils.extensions.visible
import com.technzone.bai3.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class ForgetPasswordFragment : BaseBindingFragment<FragmentForgetPasswordBinding,ForgetPasswordPresenter>(),ForgetPasswordPresenter {

    private val viewModel: ForgetPasswordViewModel by activityViewModels()
    override fun getPresenter(): ForgetPasswordPresenter = this
    override fun getLayoutId(): Int = R.layout.fragment_forget_password

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
        setUpBinding()
        setUpListeners()
    }


    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    override fun onSendCodeClicked() {
        val isValid = validateInput()
        setButtonEnable(isValid)
        if (isValid)
            viewModel.resendVerificationCode().observe(this, sendOtpResultObserver())
    }

    private fun setUpListeners() {
        binding?.edEmail?.addTextChangedListener(inputListeners)
    }

    private val inputListeners = object : TextTypingCallback {
        override fun textChanged(text: String) {
            val isValid = validateInput()
            setButtonEnable(isValid)
        }
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
                    viewModel.userIdMutableLiveData.value = data
                    navigationController.navigate(R.id.action_forgetPassword_to_verificationForgetPassword)
                }
            })
    }

    fun setButtonEnable(isEnable: Boolean) {
        viewModel.buttonEnabled.postValue(isEnable)
    }

    private fun validateInput(): Boolean {
        binding?.edEmail?.text.toString()
            .validate(ValidatorInputTypesEnums.EMAIL, requireContext()).let {
                if (!it.isValid) {
                    binding?.tvEmailError?.text = it.errorMessage
                    binding?.tvEmailError?.visible()
                    binding?.edEmail?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.edEmail?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvEmailError?.gone()
                }
            }
        return true
    }



}