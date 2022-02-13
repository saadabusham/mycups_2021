package com.raantech.mycups.ui.auth.register.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.pref.user.UserPref
import com.raantech.mycups.databinding.FragmentRegisterSuccessBinding
import com.raantech.mycups.ui.auth.register.viewmodels.RegistrationViewModel
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

@AndroidEntryPoint
class RegisterSuccessFragment : BaseBindingFragment<FragmentRegisterSuccessBinding,Nothing>() {

    private val viewModel: RegistrationViewModel by activityViewModels()

    @Inject
    lateinit var userPref: UserPref
    override fun getLayoutId(): Int = R.layout.fragment_register_success

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
        binding?.canAuth = viewModel.isTouchIdShouldVisible()
        setUpViewsListeners()
    }

    private fun setUpViewsListeners() {
        binding?.btnEnableTouchId?.setOnClickListener {
            if (viewModel.isTouchIdShouldVisible())
                userPref.setTouchIdStatus(true)
            if (requireActivity().intent.getBooleanExtra(
                    Constants.BundleData.IS_ACTIVITY_RESULT,
                    false
                )
            ) {
                requireActivity().setResult(RESULT_OK, Intent().apply {
                    this.putExtra(Constants.BundleData.IS_LOGIN_SUCCESS, true)
                })
                requireActivity().setResult(RESULT_OK)
                requireActivity().finish()
            } else {
//                MainActivity.start(requireContext())
                requireActivity().onBackPressed()
            }
        }
        binding?.tvSkip?.setOnClickListener {
            if (requireActivity().intent.getBooleanExtra(
                    Constants.BundleData.IS_ACTIVITY_RESULT,
                    false
                )
            ) {
                requireActivity().setResult(RESULT_OK, Intent().apply {
                    this.putExtra(Constants.BundleData.IS_LOGIN_SUCCESS, true)
                })
                requireActivity().setResult(RESULT_OK)
                requireActivity().finish()
            } else {
//                MainActivity.start(requireContext())
                requireActivity().onBackPressed()
            }
        }
    }
}