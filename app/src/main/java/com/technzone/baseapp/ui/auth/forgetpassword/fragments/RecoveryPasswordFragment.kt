package com.technzone.baseapp.ui.auth.forgetpassword.fragments

import androidx.fragment.app.activityViewModels
import com.technzone.baseapp.R
import com.technzone.baseapp.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.baseapp.data.common.CustomObserverResponse
import com.technzone.baseapp.databinding.FragmentRecoveryPasswordBinding
import com.technzone.baseapp.ui.base.fragment.BaseBindingFragment
import com.technzone.baseapp.utils.extensions.showErrorAlert
import com.technzone.baseapp.utils.validation.ValidatorInputTypesEnums
import com.technzone.baseapp.ui.auth.forgetpassword.viewmodels.ForgetPasswordViewModel
import com.technzone.baseapp.utils.extensions.hideKeyboard
import com.technzone.baseapp.utils.extensions.validate
import com.technzone.baseapp.utils.extensions.validateConfirmPassword
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class RecoveryPasswordFragment : BaseBindingFragment<FragmentRecoveryPasswordBinding>() {

    private val viewModel: ForgetPasswordViewModel by activityViewModels()

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
        setUpListeners()
    }


    private fun setUpData() {
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.btnContinue?.setOnClickListener {
            if (isDataValidate(true)) {
                viewModel.recoveryPassword().observe(this, recoverPasswordResultObserver())
            }
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