package com.raantech.mycups.ui.productdetails.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.OrderTypesEnum
import com.raantech.mycups.data.enums.PaymentTypeEnum
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.home.product.productdetails.ProductResponse
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderProducts
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderRequest
import com.raantech.mycups.databinding.ActivityFastProductDetailsBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.main.fragments.home.adapters.HomeItemsAdapter
import com.raantech.mycups.ui.productdetails.presenter.ProductDetailsPresenter
import com.raantech.mycups.ui.productdetails.viewmodels.ProductDetailsViewModel
import com.raantech.mycups.ui.wishlist.viewmodels.WishListViewModel
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FastProductDetailsActivity :
    BaseProductDetailsActivity<ActivityFastProductDetailsBinding>(),
    ProductDetailsPresenter {

    var designsAdapter: HomeItemsAdapter? = null
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

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    override fun onAddToCartClicked() {
        super.onAddToCartClicked()
        if (isDataValid()) {
            viewModel.createPurchaseOrder(
                PurchaseOrderRequest(
                    orderType = OrderTypesEnum.PURCHASE.value,
                    paymentMethod = PaymentTypeEnum.ONLINE_PAYMENT.value,
                    products = listOf(
                        PurchaseOrderProducts(
                            productId = viewModel.productToView.value?.id,
                            qty = viewModel.count.value
                        )
                    )
                )
            ).observe(this, orderResultObserver())
        }
    }

    private fun orderResultObserver(): CustomObserverResponse<Int> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Int> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Int?
                ) {

                }
            }
        )
    }

    override fun productDetailsResultObserver(): CustomObserverResponse<ProductResponse> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<ProductResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ProductResponse?
                ) {
                    viewModel.productToView.value = data?.product
                    handleDesigns(data?.product?.designsCategories)
                }
            }
        )
    }

    private fun handleDesigns(designs: List<DesignCategory>?) {
        if (designs.isNullOrEmpty()) {
            return
        }
        val category = Category(
            name = getString(R.string.latest_designs),
            items = designs.apply {
                forEach { it.canSelect = true }
            }
        )
        designsAdapter = HomeItemsAdapter(this)
        binding?.layoutDesigns?.rvData?.adapter = designsAdapter
        binding?.layoutDesigns?.rvData?.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {
                item as DesignCategory

            }
        })
        designsAdapter?.submitNewItems(designs)
        binding?.layoutDesigns?.item = category
        binding?.layoutDesigns?.root?.visible()
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
        if (viewModel.productToView.value?.can_upload_design == true && designsAdapter?.items?.singleOrNull { (it as DesignCategory).isSelected.value == true } == null) {
            showErrorAlert(
                title = getString(R.string.quantity),
                message = getString(R.string.please_select_the_design)
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