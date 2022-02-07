package com.technzone.bai3.ui.auth.login.fragments

import android.app.Activity
import android.content.Intent
import com.technzone.bai3.R
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.databinding.FragmentPhoneVerifiedBinding
import com.technzone.bai3.ui.auth.login.presenter.VerifiedPresenter
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.main.activity.MainActivity
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