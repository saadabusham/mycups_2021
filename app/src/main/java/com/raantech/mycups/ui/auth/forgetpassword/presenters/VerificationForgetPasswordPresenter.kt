package com.raantech.mycups.ui.auth.forgetpassword.presenters

import com.raantech.mycups.ui.base.presenters.BaseBindingPresenter

interface VerificationForgetPasswordPresenter :BaseBindingPresenter{
    fun onVerifyClicked()
    fun onResendClicked()
}