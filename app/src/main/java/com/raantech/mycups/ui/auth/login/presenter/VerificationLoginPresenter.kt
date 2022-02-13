package com.raantech.mycups.ui.auth.login.presenter

import com.raantech.mycups.ui.base.presenters.BaseBindingPresenter

interface VerificationLoginPresenter :BaseBindingPresenter{
    fun onVerifyClicked()
    fun onResendClicked()
}