package com.raantech.mycups.ui.offerdetails.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.Price
import com.raantech.mycups.data.models.home.offer.AmountPrices
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.orders.OrderDetails
import com.raantech.mycups.data.models.orders.OrderResponse
import com.raantech.mycups.databinding.FragmentOfferDetailsBinding
import com.raantech.mycups.ui.base.dialogs.CompletedDialog
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.offerdetails.adapters.OfferDetailsRecyclerAdapter
import com.raantech.mycups.ui.offerdetails.presenter.OfferDetailsPresenter
import com.raantech.mycups.ui.offerdetails.viewmodel.OfferDetailsViewModel
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class OfferDetailsFragment :
    BaseBindingFragment<FragmentOfferDetailsBinding, OfferDetailsPresenter>(),
    OfferDetailsPresenter {
    override fun getPresenter() = this

    private val viewModel: OfferDetailsViewModel by activityViewModels()
    var adapter: OfferDetailsRecyclerAdapter? = null
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    override fun getLayoutId(): Int = R.layout.fragment_offer_details

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = resources.getString(R.string.order_details)
        )
        init()
    }

    private fun init() {
        binding?.viewModel = viewModel
        setUpListeners()
        loadData()
    }

    private fun loadData() {
        requireActivity().intent.getIntExtra(Constants.BundleData.ORDER_ID, -1).let { orderId ->
            viewModel.getOrderDetails(orderId)
                .observe(this, orderDetailsObserver())
        }
    }

    private fun setUpListeners() {

    }

    override fun onPayClicked() {
        requireActivity().intent.getIntExtra(Constants.BundleData.ORDER_ID, -1).let { orderId ->
            viewModel.orderToView.value?.order?.offer_id?.let { offerId ->
                viewModel.acceptOffer(orderId, offerId).observe(this, acceptOfferObserver())
            }
        }
    }

    private fun setUpAdapter(product: Product) {
        adapter = OfferDetailsRecyclerAdapter(requireActivity(), product)
        binding?.recyclerView?.adapter = adapter
    }

    private fun orderDetailsObserver(): CustomObserverResponse<OrderResponse> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<OrderResponse> {

                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: OrderResponse?
                ) {
                    viewModel.orderToView.value = data
                    data?.order?.product?.let { setUpAdapter(it) }
                    data?.order?.measurements?.let {
                        adapter?.submitNewItems(it)
                    }
                    binding?.layoutAmount?.amountPrice = AmountPrices().apply {
                        data?.order?.vat?.let {
                            when (it) {
                                is Double -> {
                                    taxPrice = it
                                }
                                else -> {
                                    try {
                                        Gson().fromJson(it.toString(), Price::class.java)?.let {
                                            taxPrice = it.amount?.toDoubleOrNull()
                                        }
                                    } catch (e: JsonSyntaxException) {

                                    } catch (e: JsonParseException) {

                                    }
                                }
                            }
                        }
                        totalPrice = data?.order?.total?.amount?.toDoubleOrNull()
                        data?.order?.subTotal?.let {
                            when (it) {
                                is Double -> {
                                    subtotalPrice = it
                                }
                                else -> {
                                    try {
                                        Gson().fromJson(it.toString(), Price::class.java)?.let {
                                            subtotalPrice = it.amount?.toDoubleOrNull()
                                        }
                                    } catch (e: JsonSyntaxException) {

                                    } catch (e: JsonParseException) {

                                    }
                                }
                            }
                        }
                        if (data?.order?.hasStock == true) {
                            stockPrice = data.order.stockFees?.amount?.toDoubleOrNull()
                        }
                    }
                    loading.postValue(false)
                    hideShowNoData()
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    loading.postValue(false)
                    hideShowNoData()
                }

                override fun onLoading() {
                    loading.postValue(true)
                }
            }, true, showError = false
        )
    }

    private fun acceptOfferObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<Any> {

                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    showCompletedDialog()
                }
            }
        )
    }

    private fun showCompletedDialog() {
        val completedDialog =
            CompletedDialog(
                context = requireContext(),
                title = resources.getString(R.string.submit_successfully)
            )
        completedDialog.setOnDismissListener {
            requireActivity().onBackPressed()
        }
        completedDialog.show()

    }

    private fun hideShowNoData() {
        if (adapter?.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.constraintEmptyView?.visible()
        } else {
            binding?.layoutNoData?.constraintEmptyView?.gone()
            binding?.recyclerView?.visible()
        }
    }

}