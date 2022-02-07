package com.technzone.bai3.ui.auth.login.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.technzone.bai3.BuildConfig
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.TextTypingCallback
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.enums.InputFieldValidStateEnums
import com.technzone.bai3.data.models.auth.login.UserDetailsResponseModel
import com.technzone.bai3.data.pref.user.UserPref
import com.technzone.bai3.databinding.FragmentLoginBinding
import com.technzone.bai3.ui.auth.forgetpassword.ForgetPasswordActivity
import com.technzone.bai3.ui.auth.login.presenter.LoginPresenter
import com.technzone.bai3.ui.auth.login.viewmodels.LoginViewModel
import com.technzone.bai3.ui.base.bindingadapters.updateStrokeColor
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.main.activity.MainActivity
import com.technzone.bai3.utils.extensions.gone
import com.technzone.bai3.utils.extensions.validate
import com.technzone.bai3.utils.extensions.visible
import com.technzone.bai3.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseBindingFragment<FragmentLoginBinding,LoginPresenter>(),LoginPresenter {

    @Inject
    lateinit var prefs: UserPref
    private val viewModel: LoginViewModel by activityViewModels()
//    var googleLoginHandler: GoogleLoginHandler? = null

    override fun getLayoutId(): Int = R.layout.fragment_login
    override fun getPresenter(): LoginPresenter = this

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
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.empty_string
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
//        binding?.tvSkip?.setOnClickListener {
//            if (requireActivity().intent.getBooleanExtra(
//                    Constants.BundleData.IS_ACTIVITY_RESULT,
//                    false
//                )
//            ) {
//                requireActivity().finish()
//            } else {
//                MainActivity.start(requireContext())
//            }
//        }
        if(BuildConfig.DEBUG){
            viewModel.emailMutableLiveData.postValue("sa2edabusham87@gmail.com")
            viewModel.passwordMutableLiveData.postValue("P@ssw0rd")
        }
        binding?.edEmail?.addTextChangedListener(inputListeners)
        binding?.edPassword?.addTextChangedListener(inputListeners)
    }

    private val inputListeners = object : TextTypingCallback {
        override fun textChanged(text: String) {
            val isValid = validateInput()
            setButtonEnable(isValid)
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
        binding?.edEmail?.text.toString().validate(
            ValidatorInputTypesEnums.EMAIL,
            requireContext()
        ).let {
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

    fun setButtonEnable(isEnable: Boolean) {
        viewModel.buttonEnabled.postValue(isEnable)
    }

}