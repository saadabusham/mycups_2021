package com.raantech.mycups.ui.auth.forgetpassword.fragments

import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.databinding.FragmentRecoveryPasswordBinding
import com.raantech.mycups.ui.auth.forgetpassword.presenters.RecoverPasswordPresenter
import com.raantech.mycups.ui.auth.forgetpassword.viewmodels.ForgetPasswordViewModel
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.utils.extensions.hideKeyboard
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.validate
import com.raantech.mycups.utils.extensions.validateConfirmPassword
import com.raantech.mycups.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class RecoveryPasswordFragment : BaseBindingFragment<FragmentRecoveryPasswordBinding,RecoverPasswordPresenter>(),RecoverPasswordPresenter {

    private val viewModel: ForgetPasswordViewModel by activityViewModels()

    override fun getPresenter(): RecoverPasswordPresenter  = this

    override fun getLayoutId(): Int = R.layout.fragment_recovery_password

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = false,
            title = R.string.empty_string
        )
        setUpBinding()
        setUpData()
    }


    private fun setUpData() {
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    override fun onChangeClicked() {
        if (isDataValidate(true)) {
            viewModel.recoveryPassword().observe(this, recoverPasswordResultObserver())
        }
    }

    private fun recoverPasswordResultObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    binding?.root.hideKeyboard(requireActivity())
                    navigationController.navigate(R.id.action_recoveryPasswordFragment_to_recoveryPasswordSuccessFragment)
                }
            })
    }

    private fun isDataValidate(showError: Boolean): Boolean {
        var valid = true
        binding?.edPassword?.text.toString()
            .validate(ValidatorInputTypesEnums.PASSWORD, requireContext()).let {
                if (!it.isValid) {
                    requireActivity().showErrorAlert(
                        title = it.errorTitle,
                        message = it.errorMessage
                    )
                    return false
                }
            }
        binding?.edConfirmPassword?.text.toString()
            .validateConfirmPassword(
                ValidatorInputTypesEnums.CONFIRM_PASSWORD,
                binding?.edPassword?.text.toString(),
                requireContext()
            ).let {
                if (!it.isValid) {
                    requireActivity().showErrorAlert(
                        title = it.errorTitle,
                        message = it.errorMessage
                    )
                    return false
                }
            }
        return valid
    }

}