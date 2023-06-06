package com.raantech.mycups.ui.auth.login.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.raantech.mycups.BuildConfig
import com.raantech.mycups.R
import com.raantech.mycups.common.interfaces.TextTypingCallback
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.InputFieldValidStateEnums
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.pref.user.UserPref
import com.raantech.mycups.databinding.FragmentLoginBinding
import com.raantech.mycups.ui.auth.forgetpassword.ForgetPasswordActivity
import com.raantech.mycups.ui.auth.login.presenter.LoginPresenter
import com.raantech.mycups.ui.auth.login.viewmodels.LoginViewModel
import com.raantech.mycups.ui.base.bindingadapters.updateStrokeColor
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.validate
import com.raantech.mycups.utils.extensions.visible
import com.raantech.mycups.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseBindingFragment<FragmentLoginBinding,LoginPresenter>(),LoginPresenter {

    @Inject
    lateinit var prefs: UserPref
    private val viewModel: LoginViewModel by activityViewModels()
    override fun getLayoutId(): Int = R.layout.fragment_login
    override fun getPresenter(): LoginPresenter = this

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.login
        )
        prefs.setIsFirstOpen(false)
        setUpBinding()
        setUpListeners()
        checkIfThereUpdate()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    override fun onLoginClicked() {
        val isValid = validateInput()
        setButtonEnable(isValid)
        if (isValid) {
            viewModel.loginUser().observe(this, loginResultObserver())
        }
    }

    override fun onForgetPasswordClicked() {
        ForgetPasswordActivity.start(requireContext())
    }
    private fun setUpListeners() {
        if(BuildConfig.DEBUG){
            viewModel.phoneNumberMutableLiveData.postValue("0787901166")
            viewModel.passwordMutableLiveData.postValue("12345678")
        }
        binding?.edPhoneNumber?.addTextChangedListener(inputListeners)
        binding?.edPassword?.addTextChangedListener(inputListeners)
    }

    private val inputListeners = object : TextTypingCallback {
        override fun textChanged(text: String) {
            val isValid = validateInput()
            setButtonEnable(isValid)
        }
    }

    private fun loginResultObserver(): CustomObserverResponse<UserDetailsResponseModel> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<UserDetailsResponseModel> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: UserDetailsResponseModel?
                ) {
                    when (statusCode) {
                        ResponseSubErrorsCodeEnum.EmailNotVerified.value -> {
                            navigationController.navigate(
                                R.id.action_loginFragment_to_verificationLoginFragment
                            )
                        }
                        else -> {
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
                    }
                }
            })
    }


    private fun checkIfThereUpdate() {
//        if (viewModel.checkIfThereUpdate()) {
//            requireActivity().showUpdateDialog(viewModel.getUpdateStatus())
//            if (!viewModel.checkIsUpdateMandatory()) {
//                setUpTouchId()
//            }
//        } else {
//            setUpTouchId()
//        }
    }

    private fun validateInput(): Boolean {
        startTransitionDelay()
        var valid = true
        binding?.edPhoneNumber?.text.toString().validate(
            ValidatorInputTypesEnums.TEXT,
            requireContext()
        ).let {
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
//        binding?.edPassword?.text.toString().validate(
//            ValidatorInputTypesEnums.PASSWORD,
//            requireContext()
//        ).let {
//            if (!it.isValid) {
//                binding?.tvPasswordError?.text = it.errorMessage
//                binding?.tvPasswordError?.visible()
//                binding?.edPassword?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
//                return false
//            } else {
//                binding?.edPassword?.updateStrokeColor(InputFieldValidStateEnums.VALID)
//                binding?.tvPasswordError?.gone()
//            }
//        }
        return valid
    }

    fun setButtonEnable(isEnable: Boolean) {
        viewModel.buttonEnabled.postValue(isEnable)
    }

}