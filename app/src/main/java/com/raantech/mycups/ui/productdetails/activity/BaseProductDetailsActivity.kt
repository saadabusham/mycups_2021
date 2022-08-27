package com.raantech.mycups.ui.productdetails.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.home.product.productdetails.ProductResponse
import com.raantech.mycups.databinding.ActivityFastProductDetailsBinding
import com.raantech.mycups.ui.auth.AuthActivity
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.dialogs.ConfirmBottomSheet
import com.raantech.mycups.ui.productdetails.presenter.ProductDetailsPresenter
import com.raantech.mycups.ui.productdetails.viewmodels.ProductDetailsViewModel
import com.raantech.mycups.ui.wishlist.viewmodels.WishListViewModel
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseProductDetailsActivity <BINDING : ViewDataBinding> :
    BaseBindingActivity<BINDING, ProductDetailsPresenter>(),
    ProductDetailsPresenter {

    protected val viewModel: ProductDetailsViewModel by viewModels()
    protected val wishListViewModel: WishListViewModel by viewModels()

    override fun getPresenter() = this

    override fun onPlusClicked() {
        viewModel.count.value = (viewModel.count.value ?: 0) + 1
    }

    override fun onMinusClicked() {
        if (viewModel.count.value == 0) {
            return
        }
        viewModel.count.value = (viewModel.count.value ?: 1) - 1
    }

    override fun onFavoriteClicked() {
        if (!viewModel.isUserLoggedIn()) {
            showLoginDialog()
            return
        }
        viewModel.productToView.value = viewModel.productToView.value?.apply {
            isWishlist = isWishlist == false
        }
        if (viewModel.productToView.value?.isWishlist == true) {
            wishListViewModel.addToWishList(
                viewModel.productToView.value?.id
                    ?: 0
            ).observe(this, wishListObserver())
        } else {
            wishListViewModel.removeFromWishList(
                viewModel.productToView.value?.id
                    ?: 0
            ).observe(this, wishListObserver())
        }
    }

    override fun onAddToCartClicked() {
        if (!viewModel.isUserLoggedIn()) {
            showLoginDialog()
            return
        }
    }

    protected fun loadProductDetails() {
        viewModel.getProductsByID(
            intent.getIntExtra(Constants.BundleData.PRODUCT_ID, -1)
        ).observe(this, productDetailsResultObserver())
    }

    open fun productDetailsResultObserver(): CustomObserverResponse<ProductResponse> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<ProductResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ProductResponse?
                ) {
                    viewModel.productToView.value = data?.product
                }

            }
        )
    }

    private fun wishListObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<Any>?
                ) {

                }
            }, false, showError = false
        )
    }

    private fun showLoginDialog() {
        ConfirmBottomSheet(
            title = getString(R.string.you_re_not_logged_in),
            description = getString(R.string.you_need_to_login_into_your_account_to_see_this_content),
            btnConfirmTxt = getString(R.string.login),
            btnCancelTxt = getString(R.string.skip_for_now),
            object : ConfirmBottomSheet.CallBack {
                override fun onConfirmed() {
                    AuthActivity.startForResult(
                        this@BaseProductDetailsActivity,
                        true,
                        loginResultLauncher
                    )
                }

                override fun onDecline() {

                }
            }).show(supportFragmentManager, "tag")
    }

    var loginResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data?.getBooleanExtra(
                        Constants.BundleData.IS_LOGIN_SUCCESS,
                        false
                    ) == true
                ) {

                }
            }
        }

}