package com.raantech.mycups.ui.cart.fragments.cart.fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.OrderTypesEnum
import com.raantech.mycups.data.enums.PaymentTypeEnum
import com.raantech.mycups.data.models.home.offer.AmountPrices
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderProducts
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderRequest
import com.raantech.mycups.databinding.FragmentCartBinding
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.cart.fragments.cart.presenter.CartPresenter
import com.raantech.mycups.ui.cart.viewmodels.CartViewModel
import com.raantech.mycups.ui.offerdetails.adapters.OfferDetailsRecyclerAdapter
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.recycleviewutils.SwipeItemTouchCallBack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class CartFragment : BaseBindingFragment<FragmentCartBinding, CartPresenter>(), CartPresenter {

    private val viewModel: CartViewModel by activityViewModels()
    lateinit var adapter: OfferDetailsRecyclerAdapter
    override fun getPresenter() = this

    override fun getLayoutId(): Int = R.layout.fragment_cart

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.cart
        )
        binding?.viewModel = viewModel
        init()
    }

    private fun init() {
        setUpAdapter()
        loadData()
    }

    private fun loadData() {
        viewModel.getCarts().observe(this) {
            adapter.submitNewItems(it)
            calculateAmount(it)
        }
    }

    private fun calculateAmount(data: List<Product>) {
        data.sumOf { it.price?.amount?.toDoubleOrNull() ?: 0.0 }.let {
            AmountPrices(
                subtotalPrice = it,
                taxPrice = it * viewModel.TAX_CONST,
                totalPrice = it + (it * viewModel.TAX_CONST)
            ).let {
                binding?.layoutAmount?.amountPrice = it
            }
        }
    }

    override fun onPayClicked() {
        if (!viewModel.hasUserAddress()) {
            requireActivity().showErrorAlert(
                resources.getString(R.string.location),
                resources.getString(R.string.please_pick_location)
            )
            return
        }
        if (!viewModel.hasBrandName()) {
            requireActivity().showErrorAlert(
                resources.getString(R.string.brand_name),
                resources.getString(R.string.brand_name_error)
            )
            return
        }
        viewModel.createPurchaseOrder(
            PurchaseOrderRequest(
                orderType = OrderTypesEnum.PURCHASE.value,
                hasStock = false,
                paymentMethod = PaymentTypeEnum.ONLINE_PAYMENT.value,
                products = adapter.items.map {
                    PurchaseOrderProducts(
                        productId = it.id,
                        qty = it.qty
                    )
                }
            )
        ).observe(this, orderResultObserver())
    }

    private fun setUpAdapter() {
        adapter = OfferDetailsRecyclerAdapter(requireActivity())
        binding?.recyclerView?.adapter = adapter
        ItemTouchHelper(
            SwipeItemTouchCallBack(adapter,
                object : SwipeItemTouchCallBack.MoveCallBack {
                    override fun onSwipe(item: Any, position: Int) {
                        item as Product
                        adapter.removeItemAt(position)
                    }
                })
        ).attachToRecyclerView(binding?.recyclerView)
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
                    super.onSuccess(statusCode, subErrorCode, data)
                    viewModel.clearCart()
                    viewModel.orderId.value = data.toString()
                    navigationController.navigate(
                        R.id.action_cartFragment_to_orderSuccessFragment,
                        bundleOf(Pair(Constants.BundleData.ORDER_ID, data?.message))
                    )
                }
            }
        )
    }

}