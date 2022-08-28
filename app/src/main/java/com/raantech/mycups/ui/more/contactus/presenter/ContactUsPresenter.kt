package com.raantech.mycups.ui.more.contactus.presenter

import com.raantech.mycups.ui.base.presenters.BaseBindingPresenter

interface ContactUsPresenter : BaseBindingPresenter {
    fun onReasonClicked()
    fun onSelectImageClicked()
    fun onSubmitClicked()
}