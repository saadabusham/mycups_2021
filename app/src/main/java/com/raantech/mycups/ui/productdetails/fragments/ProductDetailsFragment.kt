package com.raantech.mycups.ui.productdetails.fragments

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.MediaTypesEnum
import com.raantech.mycups.data.enums.OrderTypesEnum
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.home.product.productdetails.Measurement
import com.raantech.mycups.data.models.home.product.productdetails.ProductResponse
import com.raantech.mycups.data.models.media.Media
import com.raantech.mycups.data.models.orders.request.offerorder.Files
import com.raantech.mycups.data.models.orders.request.offerorder.MeasurementsItem
import com.raantech.mycups.data.models.orders.request.offerorder.OfferOrderProduct
import com.raantech.mycups.data.models.orders.request.offerorder.OfferOrderRequest
import com.raantech.mycups.databinding.FragmentProductDetailsBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.main.fragments.home.adapters.HomeItemsAdapter
import com.raantech.mycups.ui.more.media.MediaActivity
import com.raantech.mycups.ui.productdetails.adapters.MeasurementAdapter
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class ProductDetailsFragment : BaseProductDetailsFragment<FragmentProductDetailsBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private var measurementAdapter: MeasurementAdapter? = null

    var designsAdapter: HomeItemsAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_product_details
    override fun getPresenter() = this
    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = requireActivity().intent.getStringExtra(Constants.BundleData.PRODUCT_NAME)
                ?: ""
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
            viewModel.createOfferOrder(
                OfferOrderRequest(
                    orderType = OrderTypesEnum.OFFER.value,
                    hasStock = viewModel.needStock.value,
                    files = Files(designFile = viewModel.design.value?.id),
                    product = OfferOrderProduct(
                        productId = viewModel.productToView.value?.id,
                        measurements = measurementAdapter?.items?.filter {
                            (it.count.value ?: 0) > 0
                        }?.map { MeasurementsItem(qty = it.count.value, measurementId = it.id) }
                    )
                ).apply {
                    if (viewModel.productToView.value?.can_upload_design == true) {
                        designsAdapter?.items?.singleOrNull { (it as DesignCategory).isSelected.value == true }
                            ?.let {
                                designId = (it as DesignCategory).id
                            }
                    }
                }
            ).observe(this, orderResultObserver())
        }
    }

    private fun orderResultObserver(): CustomObserverResponse<Int> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<Int> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<Int>?
                ) {
                    navigationController.navigate(
                        R.id.action_productDetailsFragment_to_orderOfferSentFragment,
                        bundleOf(Pair(Constants.BundleData.ORDER_ID, data?.message))
                    )
                }
            }
        )
    }

    private fun isDataValid(): Boolean {
        var valid = true
        if (measurementAdapter?.items?.filter { (it.count.value ?: 0) > 0 }.isNullOrEmpty()) {
            requireActivity().showErrorAlert(
                title = getString(R.string.quantity),
                message = getString(R.string.please_select_the_quantity)
            )
            return false
        }

        if (viewModel.productToView.value?.can_upload_design == true && designsAdapter?.items?.singleOrNull { (it as DesignCategory).isSelected.value == true } == null && viewModel.design.value == null) {
            requireActivity().showErrorAlert(
                title = getString(R.string.quantity),
                message = getString(R.string.please_select_the_design)
            )
            return false
        }
        if (!viewModel.hasUserAddress()) {
            requireActivity().showErrorAlert(
                resources.getString(R.string.location),
                resources.getString(R.string.please_pick_location)
            )
            return false
        }

        if (!viewModel.hasBrandName()) {
            requireActivity().showErrorAlert(
                resources.getString(R.string.brand_name),
                resources.getString(R.string.brand_name_error)
            )
            return false
        }

        return valid
    }

    override fun onSelectDesignClicked() {
        MediaActivity.start(
            requireActivity(),
            true,
            MediaTypesEnum.DESIGN.value,
            selectFileResultLauncher
        )
    }

    var selectFileResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.getSerializableExtra(Constants.BundleData.MEDIA).let {
                    it as Media
                    viewModel.design.value = it
                    binding?.tvDesign?.text = it.filename
                }
            }
        }

    private fun setUpRvMeasurement() {
        measurementAdapter = MeasurementAdapter(requireContext())
        binding?.rvMeasurement?.adapter = measurementAdapter
        binding?.rvMeasurement?.setOnItemClickListener(this)
    }

    override fun productDetailsResultObserver(): CustomObserverResponse<ProductResponse> {
        return CustomObserverResponse(
            requireActivity(),
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
        designsAdapter = HomeItemsAdapter(requireContext())
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

}