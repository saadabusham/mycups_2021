package com.raantech.mycups.ui.auth.register.presenter

import com.raantech.mycups.ui.base.presenters.BaseBindingPresenter

interface VerificationRegisterPresenter :BaseBindingPresenter{
    fun onVerifyClicked()
    fun onResendClicked()
}