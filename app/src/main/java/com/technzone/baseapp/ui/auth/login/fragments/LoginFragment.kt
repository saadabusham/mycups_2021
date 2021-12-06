package com.technzone.baseapp.ui.auth.login.fragments

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.technzone.baseapp.R
import com.technzone.baseapp.common.interfaces.TextTypingCallback
import com.technzone.baseapp.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.baseapp.data.common.Constants
import com.technzone.baseapp.data.common.CustomObserverResponse
import com.technzone.baseapp.data.enums.InputFieldValidStateEnums
import com.technzone.baseapp.data.models.auth.login.UserDetailsResponseModel
import com.technzone.baseapp.data.models.general.Countries
import com.technzone.baseapp.data.pref.user.UserPref
import com.technzone.baseapp.databinding.FragmentLoginBinding
import com.technzone.baseapp.ui.MainActivity
import com.technzone.baseapp.ui.auth.forgetpassword.ForgetPasswordActivity
import com.technzone.baseapp.ui.base.fragment.BaseBindingFragment
import com.technzone.baseapp.ui.common.countrypicker.activity.CountriesPickerActivity
import com.technzone.baseapp.utils.extensions.getDeviceCountryCode
import com.technzone.baseapp.utils.validation.ValidatorInputTypesEnums
import com.technzone.baseapp.ui.auth.login.viewmodels.LoginViewModel
import com.technzone.baseapp.ui.base.bindingadapters.updateStrokeColor
import com.technzone.baseapp.utils.extensions.gone
import com.technzone.baseapp.utils.extensions.validate
import com.technzone.baseapp.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseBindingFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var prefs: UserPref
    private val viewModel: LoginViewModel by activityViewModels()
//    var googleLoginHandler: GoogleLoginHandler? = null

    override fun getLayoutId(): Int = R.layout.fragment_login


//    override fun onResume() {
//        super.onResume()
//        try {
//            if (googleLoginHandler == null)
//                initGoogle()
//        } catch (e: Exception) {
//            e
//        }
//    }

    override fun onViewVisible() {
        super.onViewVisible()
        prefs.setIsFirstOpen(false)
        setUpBinding()
        setUpListeners()
        checkIfThereUpdate()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        requireContext().getDeviceCountryCode().let {
            viewModel.selectedCountryCode.postValue(it.code)
        }
        binding?.tvCountryCode?.setOnClickListener {
            CountriesPickerActivity.start(
                context = requireActivity(),
                currentCode = viewModel.selectedCountryCode.value,
                resultLauncher = resultLauncher
            )
        }
        binding?.btnLogin?.setOnClickListener {
            if (validateInput()) {
                viewModel.loginUser().observe(this, loginResultObserver())
            }
        }
        binding?.tvSignUp?.setOnClickListener {
//            navigationController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        tvForgetPassword?.setOnClickListener {
            ForgetPasswordActivity.start(requireContext())
        }
        binding?.btnGoogle?.setOnClickListener {
//            googleLoginHandler?.onGoogleClicked()
        }
        binding?.btnApple?.setOnClickListener {
//            navigationController.navigate(
//                R.id.action_loginFragment_to_verificationLoginFragment
//            )
        }
        binding?.tvSkip?.setOnClickListener {
            if (requireActivity().intent.getBooleanExtra(
                    Constants.BundleData.IS_ACTIVITY_RESULT,
                    false
                )
            ) {
                requireActivity().finish()
            } else {
                MainActivity.start(requireContext())
            }
        }
        binding?.edPhoneNumber?.addTextChangedListener(inputListeners)
        binding?.edPassword?.addTextChangedListener(inputListeners)
    }

    private val inputListeners = object : TextTypingCallback {
        override fun textChanged(text: String) {
            validateInput()
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.selectedCountryCode.postValue((data?.getSerializableExtra(Constants.BundleData.COUNTRY) as Countries).code)
            }
        }

//    private fun initGoogle() {
//        googleLoginHandler =
//            GoogleLoginHandler.getInstance(requireActivity() as AuthActivity).apply {
//                setRegisterResult(googleLoginResultLauncher)
//            }
//        googleLoginHandler?.showError(true)
//        googleLoginHandler?.setCallBack(object : SocialLoginCallBack {
//            override fun onSuccess(provider: String, token: String) {
//                viewModel.socialLoginUser(
//                    accessToken = token,
//                    provider = provider
//                ).observe(requireActivity(), loginResultObserver(true))
//            }
//        })
//        googleLoginHandler?.initGoogle()
//    }
//
//    private var googleLoginResultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                googleLoginHandler?.setResult(task)
//            }
//        }

    private fun loginResultObserver(isSocialLogin: Boolean = false): CustomObserverResponse<UserDetailsResponseModel> {
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
                            viewModel.userIdMutableLiveData.postValue(data?.id)
//                            navigationController.navigate(
//                                R.id.action_loginFragment_to_verificationLoginFragment
//                            )
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
            ValidatorInputTypesEnums.PHONE_NUMBER,
            requireContext()
        ).let {
            if (!it.isValid) {
                binding?.tvPhoneError?.text = it.errorMessage
                binding?.tvPhoneError?.visible()
                binding?.linearMobile?.setBackgroundResource(R.drawable.shape_edittext_error)
                return false
            } else {
                linearMobile.setBackgroundResource(R.drawable.shape_edittext)
                binding?.tvPhoneError?.gone()
            }
        }
        binding?.edPassword?.text.toString().validate(
            ValidatorInputTypesEnums.PASSWORD,
            requireContext()
        ).let {
            if (!it.isValid) {
                binding?.tvPasswordError?.text = it.errorMessage
                binding?.tvPasswordError?.visible()
                binding?.edPassword?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                return false
            } else {
                binding?.edPassword?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                binding?.tvPasswordError?.gone()
            }
        }
        return valid
    }

}