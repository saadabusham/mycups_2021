package com.technzone.bai3.ui.auth.register.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.TextTypingCallback
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.enums.InputFieldValidStateEnums
import com.technzone.bai3.data.models.general.Countries
import com.technzone.bai3.databinding.FragmentRegisterBinding
import com.technzone.bai3.ui.auth.register.viewmodels.RegistrationViewModel
import com.technzone.bai3.ui.base.bindingadapters.updateStrokeColor
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.utils.extensions.*
import com.technzone.bai3.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class RegistrationFragment :
    BaseBindingFragment<FragmentRegisterBinding>() {

    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_register

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
        binding?.viewModel = viewModel
        setUpData()
        setUpListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    private fun setUpListener() {
        binding?.btnSignUp?.setOnClickListener {
            val isValid = isDataValidate()
            setButtonEnable(isValid)
            if (isValid)
                viewModel.registerUser().observe(this, registerResultObserver())
        }
        requireContext().getDeviceCountryCode().let {
            viewModel.selectedCountryCode.postValue(it)
        }
        binding?.edFirstName?.addTextChangedListener(inputListeners)
        binding?.edLastName?.addTextChangedListener(inputListeners)
        binding?.edEmail?.addTextChangedListener(inputListeners)
        binding?.edPhoneNumber?.addTextChangedListener(inputListeners)
        binding?.etPassword?.addTextChangedListener(inputListeners)
        binding?.etConfirmPassword?.addTextChangedListener(inputListeners)
    }

    private val inputListeners = object : TextTypingCallback {
        override fun textChanged(text: String) {
            val isValid = isDataValidate()
            setButtonEnable(isValid)
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.selectedCountryCode.postValue(data?.getSerializableExtra(Constants.BundleData.COUNTRY) as Countries)
            }
        }

    private fun setUpData() {
        val localData: List<Countries> =
            readRawJson(requireContext(), R.raw.countries)
    }

    private fun registerResultObserver(): CustomObserverResponse<String> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<String> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: String?
                ) {
                    viewModel.userIdMutableLiveData.value = data
                    navigationController.navigate(R.id.action_registrationFragment_to_verificationSignUpFragment)
                }
            })
    }

    fun setButtonEnable(isEnable: Boolean) {
        viewModel.buttonEnabled.postValue(isEnable)
    }

    private fun isDataValidate(): Boolean {
        startTransitionDelay()
        var valid = true
        binding?.edFirstName?.text.toString()
            .validate(ValidatorInputTypesEnums.FIRST_NAME, requireContext()).let {
                if (!it.isValid) {
                    binding?.tvFirstNameError?.text = it.errorMessage
                    binding?.tvFirstNameError?.visible()
                    binding?.edFirstName?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.edFirstName?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvFirstNameError?.gone()
                }
            }
        binding?.edLastName?.text.toString()
            .validate(ValidatorInputTypesEnums.LAST_NAME, requireContext()).let {
                if (!it.isValid) {
                    binding?.tvLastNameError?.text = it.errorMessage
                    binding?.tvLastNameError?.visible()
                    binding?.edLastName?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.edLastName?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvLastNameError?.gone()
                }
            }
        binding?.edPhoneNumber?.text.toString()
            .validate(ValidatorInputTypesEnums.PHONE_NUMBER, requireContext()).let {
                if (!it.isValid) {
                    binding?.tvPhoneNumberError?.text = it.errorMessage
                    binding?.tvPhoneNumberError?.visible()
                    binding?.edPhoneNumber?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.edPhoneNumber?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvPhoneNumberError?.gone()
                }
            }
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
        binding?.etPassword?.text.toString()
            .validate(ValidatorInputTypesEnums.PASSWORD, requireContext()).let {
                if (!it.isValid) {
                    binding?.tvPasswordError?.text = it.errorMessage
                    binding?.tvPasswordError?.visible()
                    binding?.etPassword?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.etPassword?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvPasswordError?.gone()
                }
            }
        binding?.etConfirmPassword?.text.toString()
            .validateConfirmPassword(
                ValidatorInputTypesEnums.CONFIRM_PASSWORD,
                binding?.etPassword?.text.toString(),
                requireContext()
            ).let {
                if (!it.isValid) {
                    binding?.tvConfirmPasswordError?.text = it.errorMessage
                    binding?.tvConfirmPasswordError?.visible()
                    binding?.etConfirmPassword?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.etPassword?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvConfirmPasswordError?.gone()
                }
            }
        return valid
    }

}