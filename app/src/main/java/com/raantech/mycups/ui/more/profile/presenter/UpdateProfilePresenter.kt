package com.raantech.mycups.ui.more.profile.presenter

import com.raantech.mycups.ui.base.presenters.BaseBindingPresenter

interface UpdateProfilePresenter :BaseBindingPresenter{
    fun onEditClicked()
    fun onAddressClicked()
    fun onChangePasswordClicked(){}
    fun onSelectStorageClicked(){}
}