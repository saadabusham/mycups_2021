package com.raantech.mycups.ui.auth.forgetpassword.fragments

import com.raantech.mycups.R
import com.raantech.mycups.databinding.FragmentRecoveryPasswordSuccessBinding
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class RecoveryPasswordSuccessFragment :
    BaseBindingFragment<FragmentRecoveryPasswordSuccessBinding,Nothing>() {

    override fun getLayoutId(): Int = R.layout.fragment_recovery_password_success

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
    }
}