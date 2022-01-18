package com.technzone.bai3.ui.auth.register.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.data.pref.user.UserPref
import com.technzone.bai3.databinding.FragmentRegisterSuccessBinding
import com.technzone.bai3.ui.MainActivity
import com.technzone.bai3.ui.auth.register.viewmodels.RegistrationViewModel
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

@AndroidEntryPoint
class RegisterSuccessFragment : BaseBindingFragment<FragmentRegisterSuccessBinding>() {

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
                MainActivity.start(requireContext())
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
                MainActivity.start(requireContext())
            }
        }
    }
}