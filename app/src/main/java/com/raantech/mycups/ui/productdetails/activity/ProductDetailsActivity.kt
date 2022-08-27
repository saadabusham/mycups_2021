package com.raantech.mycups.ui.productdetails.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.product.productdetails.Measurement
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.home.product.productdetails.ProductResponse
import com.raantech.mycups.databinding.ActivityFastProductDetailsBinding
import com.raantech.mycups.databinding.ActivityProductDetailsBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.main.fragments.home.adapters.HomeItemsAdapter
import com.raantech.mycups.ui.productdetails.adapters.MeasurementAdapter
import com.raantech.mycups.ui.productdetails.presenter.ProductDetailsPresenter
import com.raantech.mycups.ui.productdetails.viewmodels.ProductDetailsViewModel
import com.raantech.mycups.ui.wishlist.viewmodels.WishListViewModel
import com.raantech.mycups.utils.ContentUriUtils.getFilePathFromURI
import com.raantech.mycups.utils.FileUtil.getFileNameFromUri
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity :
    BaseProductDetailsActivity<ActivityProductDetailsBinding>(),
    ProductDetailsPresenter, BaseBindingRecyclerViewAdapter.OnItemClickListener {
    private var measurementAdapter: MeasurementAdapter? = null

    var designsAdapter: HomeItemsAdapter? = null

    override fun getPresenter() = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_product_details,
            hasToolbar = true,
            showBackArrow = true,
            hasBackButton = true,
            titleString = intent.getStringExtra(Constants.BundleData.PRODUCT_NAME) ?: ""
        )
        setUpBinding()
        setUpRvMeasurement()
        loadProductDetails()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    override fun onAddToCartClicked() {
        super.onAddToCartClicked()
        if (isDataValid()) {

        }
    }

    private fun isDataValid(): Boolean {
        var valid = true
        if (measurementAdapter?.items?.filter { (it.count.value ?: 0) > 0 }.isNullOrEmpty()) {
            showErrorAlert(
                title = getString(R.string.quantity),
                message = getString(R.string.please_select_the_quantity)
            )
            return false
        }
        return valid
    }

    override fun onSelectDesignClicked() {
        openDocumentPicker()
    }

    private fun openDocumentPicker() {
        val intent = Intent("com.sec.android.app.myfiles.PICK_DATA")
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        val mimetypes = arrayOf("application/pdf")
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra("CONTENT_TYPE", "application/pdf");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        frontImageFileResultLauncher.launch(Intent.createChooser(intent, "Choose Pdf"))
    }

    var frontImageFileResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                var realPath = data?.data?.getFilePathFromURI(this)
                if (realPath == null)
                    realPath = data?.data?.toString()
                realPath?.let {
                    viewModel.design.value = it
                    binding?.tvDesign?.text = getFileNameFromUri(realPath)
                }

            }
        }

    private fun setUpRvMeasurement() {
        measurementAdapter = MeasurementAdapter(this)
        binding?.rvMeasurement?.adapter = measurementAdapter
        binding?.rvMeasurement?.setOnItemClickListener(this)
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
                    measurementAdapter?.submitNewItems(data?.product?.measurements)
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
        binding?.layoutDesigns?.rvData?.setOnItemClickListener(this)
        designsAdapter?.submitNewItems(designs)
        binding?.layoutDesigns?.item = category
        binding?.layoutDesigns?.root?.visible()
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        when (item) {
            is Measurement -> {

            }
            is DesignCategory -> {

            }
        }
    }

    companion object {

        fun start(context: Context?, productId: Int, productName: String?) {
            val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                putExtra(Constants.BundleData.PRODUCT_ID, productId)
                putExtra(Constants.BundleData.PRODUCT_NAME, productName ?: "")
            }
            context?.startActivity(intent)
        }
    }

}