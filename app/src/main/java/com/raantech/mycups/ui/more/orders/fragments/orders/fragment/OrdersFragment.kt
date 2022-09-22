package com.raantech.mycups.ui.more.orders.fragments.orders.fragment

import android.view.View
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.OrderStatusEnum
import com.raantech.mycups.data.enums.OrderTypesEnum
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.home.product.productdetails.ProductsResponse
import com.raantech.mycups.data.models.orders.Order
import com.raantech.mycups.data.models.orders.OrdersResponse
import com.raantech.mycups.databinding.FragmentOrdersBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.more.orders.fragments.orders.adapters.OrdersRecyclerAdapter
import com.raantech.mycups.ui.more.orders.viewmodels.OrdersViewModel
import com.raantech.mycups.ui.offerdetails.activity.OfferDetailsActivity
import com.raantech.mycups.ui.productdetails.activity.ProductDetailsActivity
import com.raantech.mycups.ui.subcategory.adapter.ProductVerticalRecyclerAdapter
import com.raantech.mycups.ui.subcategory.adapter.TabListRecyclerAdapter
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.visible
import com.raantech.mycups.utils.plus
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class OrdersFragment : BaseBindingFragment<FragmentOrdersBinding, Nothing>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {


    lateinit var tabListRecyclerAdapter: TabListRecyclerAdapter
    lateinit var productVerticalRecyclerAdapter: OrdersRecyclerAdapter
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val viewModel: OrdersViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_orders

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.menu_my_orders
        )
        setUpBinding()
        setUpSwipeRefresh()
        observeLoading()
        setUpRvOrders()
        setUpRvTabs()
        initSearch()
    }

    override fun onResume() {
        super.onResume()
        applyFilter(viewModel.searchText.value)
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpSwipeRefresh() {
        binding?.swipeRefresh?.setOnRefreshListener {
            if(loading.value == true){
                binding?.swipeRefresh?.isRefreshing = false
                return@setOnRefreshListener
            }
            applyFilter(viewModel.searchText.value)
        }
    }

    private fun setUpRvTabs() {
        tabListRecyclerAdapter = TabListRecyclerAdapter(requireActivity())
        binding?.rvTabs?.adapter = tabListRecyclerAdapter
        binding?.rvTabs?.setOnItemClickListener(this)
        tabListRecyclerAdapter.submitNewItems(
            listOf(
                CategoriesItem(
                    value = OrderTypesEnum.OFFER.value,
                    name = getString(R.string.requests_for_quotations),
                    isSelected = MutableLiveData(true)
                ),
                CategoriesItem(
                    value = OrderTypesEnum.PURCHASE.value,
                    name = getString(R.string.purchase_orders)
                )
            )
        )
    }

    private fun setUpRvOrders() {
        productVerticalRecyclerAdapter = OrdersRecyclerAdapter(requireActivity())
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

    private fun applyFilter(text: String?) {
        viewModel.searchText.value = text
        tabListRecyclerAdapter.getSelectedItem()?.let {
            loadOrders(it, text)
        }
    }

    private fun loadOrders(categoriesItem: CategoriesItem, name: String? = null) {
        categoriesItem.value?.let {
            viewModel.getOrders(it, name)
                .observe(this, ordersResultObserver())
        }
    }

    private fun ordersResultObserver(): CustomObserverResponse<OrdersResponse> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<OrdersResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: OrdersResponse?
                ) {
                    loading.postValue(false)
                    productVerticalRecyclerAdapter.submitNewItems(data?.orders)
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
                binding?.swipeRefresh?.isRefreshing = false
            }
        }
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        when (item) {
            is CategoriesItem -> {
                binding?.rvTabs?.smoothScrollToPosition(position)
                productVerticalRecyclerAdapter.clear()
                hideShowNoData(true)
                loadOrders(item)
            }
            is Order -> {
                item.id?.let { orderId ->
                    tabListRecyclerAdapter.getSelectedItem()?.let {
                        if (it.value == OrderTypesEnum.OFFER.value && item.status != OrderStatusEnum.PENDING.value) {
                            OfferDetailsActivity.start(
                                requireContext(),
                                orderId
                            )
                        }
                    }
                }
            }
        }
    }

}