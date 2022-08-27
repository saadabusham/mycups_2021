package com.raantech.mycups.ui.productdetails.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.databinding.ActivityFastProductDetailsBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.productdetails.presenter.ProductDetailsPresenter
import com.raantech.mycups.ui.productdetails.viewmodels.ProductDetailsViewModel
import com.raantech.mycups.ui.wishlist.viewmodels.WishListViewModel
import com.raantech.mycups.utils.extensions.showErrorAlert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FastProductDetailsActivity :
    BaseProductDetailsActivity<ActivityFastProductDetailsBinding>(),
    ProductDetailsPresenter {

    override fun getPresenter() = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_fast_product_details,
            hasToolbar = true,
            showBackArrow = true,
            hasBackButton = true,
            titleString = intent.getStringExtra(Constants.BundleData.PRODUCT_NAME) ?: ""
        )
        setUpBinding()
        loadProductDetails()
    }

    override fun onAddToCartClicked() {
        super.onAddToCartClicked()
        if (isDataValid()) {

        }
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun isDataValid(): Boolean {
        var valid = true
        if ((viewModel.count.value ?: 0) < 1) {
            showErrorAlert(
                title = getString(R.string.quantity),
                message = getString(R.string.please_select_the_quantity)
            )
            return false
        }
        return valid
    }

    companion object {

        fun start(context: Context?, productId: Int, productName: String?) {
            val intent = Intent(context, FastProductDetailsActivity::class.java).apply {
                putExtra(Constants.BundleData.PRODUCT_ID, productId)
                putExtra(Constants.BundleData.PRODUCT_NAME, productName ?: "")
            }
            context?.startActivity(intent)
        }
    }

}