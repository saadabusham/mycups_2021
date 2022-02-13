package com.raantech.mycups.ui.auth.login.presenter

import com.raantech.mycups.ui.base.presenters.BaseBindingPresenter

interface LoginPresenter :BaseBindingPresenter{
    fun onLoginClicked()
    fun onForgetPasswordClicked()
}