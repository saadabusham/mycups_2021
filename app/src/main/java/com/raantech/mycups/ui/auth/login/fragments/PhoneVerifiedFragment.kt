package com.raantech.mycups.ui.auth.login.fragments

import android.app.Activity
import android.content.Intent
import com.raantech.mycups.R
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.databinding.FragmentPhoneVerifiedBinding
import com.raantech.mycups.ui.auth.login.presenter.VerifiedPresenter
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.main.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneVerifiedFragment :
    BaseBindingFragment<FragmentPhoneVerifiedBinding,VerifiedPresenter>(),VerifiedPresenter {

    override fun getLayoutId(): Int = R.layout.fragment_phone_verified
    override fun getPresenter(): VerifiedPresenter = this

    override fun onViewVisible() {
        super.onViewVisible()
    }

    override fun onContinueClicked() {
        binding?.btnContinue?.setOnClickListener {
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
                MainActivity.start(requireContext())
            }
        }
    }
}