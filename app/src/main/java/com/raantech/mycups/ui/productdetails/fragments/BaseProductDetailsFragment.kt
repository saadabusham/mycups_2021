package com.raantech.mycups.ui.productdetails.fragments

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.home.product.productdetails.ProductResponse
import com.raantech.mycups.ui.auth.AuthActivity
import com.raantech.mycups.ui.base.dialogs.ConfirmBottomSheet
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.productdetails.presenter.ProductDetailsPresenter
import com.raantech.mycups.ui.productdetails.viewmodels.ProductDetailsViewModel
import com.raantech.mycups.ui.wishlist.viewmodels.WishListViewModel

abstract class BaseProductDetailsFragment<BINDING : ViewDataBinding> :
    BaseBindingFragment<BINDING, ProductDetailsPresenter>(),
    ProductDetailsPresenter {

    protected val viewModel: ProductDetailsViewModel by activityViewModels()
    protected val wishListViewModel: WishListViewModel by activityViewModels()

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

    override fun onSelectStorageClicked() {
        if (viewModel.needStock.value == false) {
            showStorageHintDialog()
        } else {
            viewModel.needStock.value = false
        }
    }

    protected fun loadProductDetails() {
        viewModel.getProductsByID(
            requireActivity().intent.getIntExtra(Constants.BundleData.PRODUCT_ID, -1)
        ).observe(this, productDetailsResultObserver())
    }

    open fun productDetailsResultObserver(): CustomObserverResponse<ProductResponse> {
        return CustomObserverResponse(
            requireActivity(),
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
            requireActivity(),
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

    private fun showStorageHintDialog() {
        ConfirmBottomSheet(
            title = getString(R.string.storage_information),
            description = getString(R.string.storage_information_desc),
            btnConfirmTxt = getString(R.string.agree),
            btnCancelTxt = getString(R.string.disagree),
            object : ConfirmBottomSheet.CallBack {
                override fun onConfirmed() {
                    viewModel.needStock.value = true
                }

                override fun onDecline() {

                }
            }).show(childFragmentManager, "tag")
    }

}