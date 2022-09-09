package com.raantech.mycups.ui.subcategory.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.category.CategoriesResponse
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.home.product.productdetails.ProductsResponse
import com.raantech.mycups.databinding.ActivitySubcategoryBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.productdetails.activity.ProductDetailsActivity
import com.raantech.mycups.ui.subcategory.adapter.ProductVerticalRecyclerAdapter
import com.raantech.mycups.ui.subcategory.adapter.TabListRecyclerAdapter
import com.raantech.mycups.ui.subcategory.viewmodels.SubcategoryViewModel
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubcategoryActivity : BaseBindingActivity<ActivitySubcategoryBinding, Nothing>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: SubcategoryViewModel by viewModels()

    lateinit var tabListRecyclerAdapter: TabListRecyclerAdapter
    lateinit var productVerticalRecyclerAdapter: ProductVerticalRecyclerAdapter
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var category: CategoriesItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntentData()
        setContentView(
            layoutResID = R.layout.activity_subcategory,
            hasToolbar = true,
            hasTitle = true,
            showBackArrow = true,
            hasBackButton = true,
            titleString = category?.name ?: ""
        )
        setUpBinding()
        observeLoading()
        setUpRvTabs()
        setUpRvProduct()
        loadData()
    }

    private fun handleIntentData() {
        intent.getSerializableExtra(Constants.BundleData.CATEGORY)?.let {
            it as CategoriesItem
            category = it
        }
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpRvTabs() {
        tabListRecyclerAdapter = TabListRecyclerAdapter(this)
        binding?.rvTabs?.adapter = tabListRecyclerAdapter
        binding?.rvTabs?.setOnItemClickListener(this)
    }

    private fun setUpRvProduct() {
        productVerticalRecyclerAdapter = ProductVerticalRecyclerAdapter(this)
        binding?.rvProducts?.adapter = productVerticalRecyclerAdapter
        binding?.rvProducts?.itemAnimator = null
        binding?.rvProducts?.setOnItemClickListener(this)
    }

    private fun loadData() {
        if (category?.hasSub == true) {
            productVerticalRecyclerAdapter.clear()
            loadCategories()
        } else {
            category?.id?.let { loadProducts(it) }
        }
    }

    private fun loadCategories() {
        category?.id?.let {
            viewModel.getSubCategories(
                it
            ).observe(this, categoriesResultObserver())
        }
    }

    private fun categoriesResultObserver(): CustomObserverResponse<CategoriesResponse> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<CategoriesResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: CategoriesResponse?
                ) {
                    loading.postValue(false)
                    data?.categories?.let {
                        tabListRecyclerAdapter.submitNewItems(it)
                        handleCategoriesResult(0)
                    }
                    checkTabList()
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    loading.postValue(false)
                    checkTabList()
                }

                override fun onLoading() {
                    super.onLoading()
                    loading.postValue(true)
                }
            }, showError = true, withProgress = false
        )
    }

    private fun handleCategoriesResult(selectedPosition: Int) {
        if (tabListRecyclerAdapter.items.isEmpty()) {
            return
        }
        tabListRecyclerAdapter.items[selectedPosition].let {
            it.isSelected.value = true
            it.id?.let { it1 -> loadProducts(it1) }
        }
    }

    private fun checkTabList() {
        if (tabListRecyclerAdapter.itemCount > 0) {
            binding?.layoutEmptyWishlist?.constraintEmptyView?.gone()
        } else
            binding?.layoutEmptyWishlist?.constraintEmptyView?.visible()
    }

    private fun loadProducts(categoriesId: Int) {
        categoriesId.let {
            viewModel.getProducts(it)
                .observe(this, productsResultObserver())
        }
    }

    private fun productsResultObserver(): CustomObserverResponse<ProductsResponse> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<ProductsResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ProductsResponse?
                ) {
                    loading.postValue(false)
                    productVerticalRecyclerAdapter.submitNewItems(data?.products)
                    hideShowNoData()
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    loading.postValue(false)
                    hideShowNoData()
                }

                override fun onLoading() {
                    super.onLoading()
                    loading.postValue(true)
                }
            }, showError = false, withProgress = false
        )
    }

    private fun hideShowNoData(hide: Boolean = false) {
        if (productVerticalRecyclerAdapter.itemCount == 0 && !hide)
            binding?.layoutEmptyWishlist?.constraintEmptyView?.visible()
        else
            binding?.layoutEmptyWishlist?.constraintEmptyView?.gone()
    }

    private fun observeLoading() {
        loading.observe(this) {
            if (it) {
                binding?.layoutShimmer?.shimmerViewContainer?.visible()
                binding?.layoutShimmer?.shimmerViewContainer?.startShimmer()
            } else {
                binding?.layoutShimmer?.shimmerViewContainer?.gone()
                binding?.layoutShimmer?.shimmerViewContainer?.stopShimmer()
            }
        }
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        when (item) {
            is CategoriesItem -> {
                checkTabList()
                binding?.rvTabs?.smoothScrollToPosition(position)
                productVerticalRecyclerAdapter.clear()
                hideShowNoData(true)
                item.id?.let { it1 ->
                    loadProducts(item.id)
                }
            }
            is Product -> {
                item.id?.let {
                    ProductDetailsActivity.start(
                        this,
                        it, item.name, item.is_fast
                    )
                }
            }
        }
    }

    companion object {

        fun start(context: Context?, category: CategoriesItem) {
            val intent = Intent(context, SubcategoryActivity::class.java).apply {
                putExtra(Constants.BundleData.CATEGORY, category)
            }
            context?.startActivity(intent)
        }
    }

}