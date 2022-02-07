package com.technzone.bai3.ui.auth.register.presenter

import com.technzone.bai3.ui.base.presenters.BaseBindingPresenter

interface VerificationRegisterPresenter :BaseBindingPresenter{
    fun onVerifyClicked()
    fun onResendClicked()
}