package com.raantech.mycups.ui.productdetails.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.home.product.productdetails.ProductResponse
import com.raantech.mycups.databinding.FragmentFastProductDetailsBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.cart.viewmodels.CartViewModel
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.ui.main.fragments.home.adapters.HomeItemsAdapter
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class FastProductDetailsFragment : BaseProductDetailsFragment<FragmentFastProductDetailsBinding>() {

    var designsAdapter: HomeItemsAdapter? = null
    val cartViewModel: CartViewModel by activityViewModels()
    override fun getLayoutId(): Int = R.layout.fragment_fast_product_details
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
        loadProductDetails()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    override fun onAddToCartClicked() {
        if (!viewModel.isUserLoggedIn()) {
            showLoginDialog()
            return
        }
        if (isDataValid()) {
            viewModel.productToView.value?.let {
                cartViewModel.saveCart(it.apply {
                    qty = viewModel.count.value
                })
                MainActivity.start(requireContext())
            }
        }
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
        designsAdapter = HomeItemsAdapter(requireActivity())
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
            requireActivity().showErrorAlert(
                title = getString(R.string.quantity),
                message = getString(R.string.please_select_the_quantity)
            )
            return false
        }
        if (viewModel.productToView.value?.can_upload_design == true && designsAdapter?.items?.singleOrNull { (it as DesignCategory).isSelected.value == true } == null) {
            requireActivity().showErrorAlert(
                title = getString(R.string.quantity),
                message = getString(R.string.please_select_the_design)
            )
            return false
        }
        return valid
    }

}