package com.raantech.mycups.ui.search.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.category.CategoriesResponse
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.home.product.productdetails.ProductsResponse
import com.raantech.mycups.databinding.ActivitySearchBinding
import com.raantech.mycups.databinding.ActivitySubcategoryBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.more.wishlist.viewmodels.WishListViewModel
import com.raantech.mycups.ui.productdetails.activity.ProductDetailsActivity
import com.raantech.mycups.ui.search.viewmodels.SearchViewModel
import com.raantech.mycups.ui.subcategory.adapter.ProductVerticalRecyclerAdapter
import com.raantech.mycups.ui.subcategory.adapter.TabListRecyclerAdapter
import com.raantech.mycups.ui.subcategory.viewmodels.SubcategoryViewModel
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.visible
import com.raantech.mycups.utils.plus
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchActivity : BaseBindingActivity<ActivitySearchBinding, Nothing>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: SearchViewModel by viewModels()
    private val wishListViewModel: WishListViewModel by viewModels()

    lateinit var productVerticalRecyclerAdapter: ProductVerticalRecyclerAdapter
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    var positionToUpdate: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_search,
            hasToolbar = true,
            showBackArrow = true,
            hasBackButton = true,
            hasTitle = true,
            title = R.string.search
        )
        setUpBinding()
        observeLoading()
        initSearch()
        setUpRvProduct()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpRvProduct() {
        productVerticalRecyclerAdapter = ProductVerticalRecyclerAdapter(this)
        binding?.rvProducts?.adapter = productVerticalRecyclerAdapter
        binding?.rvProducts?.itemAnimator = null
        binding?.rvProducts?.setOnItemClickListener(this)
    }

    private fun initSearch() {
        viewModel.compositeDisposable + binding?.edSearch?.textChangeEvents()
            ?.skipInitialValue()
            ?.debounce(400, TimeUnit.MILLISECONDS)
            ?.distinctUntilChanged()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe {
                if (it.text.isNotEmpty()) {
                    applyFilter(it.text.toString())
                } else {
                    applyFilter("")
                }
            }
    }

    private fun applyFilter(text: String) {
        productVerticalRecyclerAdapter.clear()
        loadProducts(text)
    }

    private fun loadProducts(searchText:String = "") {
        viewModel.getProducts(searchText)
            .observe(this, productsResultObserver())
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

    private fun wishListActionObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<Any>?
                ) {
                    productVerticalRecyclerAdapter.items[positionToUpdate].isWishlist =
                        productVerticalRecyclerAdapter.items[positionToUpdate].isWishlist != true
                    productVerticalRecyclerAdapter.notifyItemChanged(positionToUpdate)
                    positionToUpdate = -1
                }
            }, false
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        when (item) {
            is Product -> {
                var isWishList = item.isWishlist == true
                var id = item.id ?: 0
                positionToUpdate = position
                if (view?.id == R.id.imgFavorite) {
                    if (isWishList) {
                        wishListViewModel.removeFromWishList(id).observe(this, wishListActionObserver())
                    } else {
                        wishListViewModel.addToWishList(
                            id
                        ).observe(this, wishListActionObserver())
                    }
                } else {
                    item.id?.let {
                        ProductDetailsActivity.start(
                            this,
                            it, item.name, item.is_fast
                        )
                    }
                }
            }
        }
    }

    companion object {

        fun start(context: Context?) {
            val intent = Intent(context, SearchActivity::class.java)
            context?.startActivity(intent)
        }
    }

}