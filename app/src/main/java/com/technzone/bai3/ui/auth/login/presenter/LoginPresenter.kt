package com.technzone.bai3.ui.auth.login.presenter

import com.technzone.bai3.ui.base.presenters.BaseBindingPresenter

interface LoginPresenter :BaseBindingPresenter{
    fun onLoginClicked()
    fun onForgetPasswordClicked()
}