package com.technzone.baseapp.ui.auth.forgetpassword.fragments

import com.technzone.baseapp.R
import com.technzone.baseapp.databinding.FragmentRecoveryPasswordSuccessBinding
import com.technzone.baseapp.ui.base.fragment.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoveryPasswordSuccessFragment :
    BaseBindingFragment<FragmentRecoveryPasswordSuccessBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_recovery_password_success

    override fun onViewVisible() {
        super.onViewVisible()
        setUpViewsListeners()
    }

    private fun setUpViewsListeners() {
        binding?.btnLogin?.setOnClickListener {
            requireActivity().finish()
        }
    }
}