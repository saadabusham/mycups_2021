package com.raantech.mycups.ui.productdetails.presenter

import com.raantech.mycups.ui.base.presenters.BaseBindingPresenter

interface ProductDetailsPresenter :BaseBindingPresenter{
    fun onAddToCartClicked()
    fun onFavoriteClicked()
    fun onPlusClicked(){}
    fun onMinusClicked(){}
    fun onSelectDesignClicked(){}
}