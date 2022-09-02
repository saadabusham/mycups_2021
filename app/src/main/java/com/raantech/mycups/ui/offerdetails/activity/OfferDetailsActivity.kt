package com.raantech.mycups.ui.offerdetails.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.home.offer.OfferDetails
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.databinding.ActivityOfferDetailsBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.offerdetails.adapters.OfferDetailsRecyclerAdapter
import com.raantech.mycups.ui.offerdetails.presenter.OfferDetailsPresenter
import com.raantech.mycups.ui.offerdetails.viewmodel.OfferDetailsViewModel
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.visible
import com.raantech.mycups.utils.recycleviewutils.SwipeItemTouchCallBack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferDetailsActivity :
    BaseBindingActivity<ActivityOfferDetailsBinding, OfferDetailsPresenter>(),
    OfferDetailsPresenter {

    private val viewModel: OfferDetailsViewModel by viewModels()
    lateinit var adapter: OfferDetailsRecyclerAdapter
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false

    override fun getPresenter() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_offer_details,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = resources.getString(R.string.empty_string)
        )
        init()
    }

    private fun init() {
        setUpListeners()
        setUpAdapter()
        loadData()
    }

    private fun loadData() {
        intent.getStringExtra(Constants.BundleData.OFFER_ID)?.let {
            viewModel.getOfferDetails(it)
                .observe(this, offerDetailsObserver())
        }
    }

    private fun setUpListeners() {

    }

    override fun onPayClicked() {

    }

    private fun setUpAdapter() {
        adapter = OfferDetailsRecyclerAdapter(this)
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
        Paginate.with(binding?.recyclerView, object : Paginate.Callbacks {
            override fun onLoadMore() {
                if (loading.value == false && adapter.itemCount > 0 && !isFinished) {
                    loadData()
                }
            }

            override fun isLoading(): Boolean {
                return loading.value ?: false
            }

            override fun hasLoadedAllItems(): Boolean {
                return isFinished
            }

        })
            .setLoadingTriggerThreshold(1)
            .addLoadingListItem(false)
            .setLoadingListItemCreator(object : LoadingListItemCreator {
                override fun onCreateViewHolder(
                    parent: ViewGroup?,
                    viewType: Int
                ): RecyclerView.ViewHolder {
                    val view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.loading_row, parent, false)
                    return object : RecyclerView.ViewHolder(view) {}
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

                }

            })
            .build()

        adapter.submitItems(
            arrayListOf(
                Product(
                    name = "تم ارسال تسعير للطلب رقم #3321",
                ),
                Product(
                    name = "لقد تم اضافة منتجات جديدة تصفحها الان",
                ),
                Product(
                    name = "نود تذكيرك بان لديك في مخازننا اكواب عدد 10000",
                ),
                Product(
                    name = "لقد تم توصيل منتجاتك الان الى المخزن"
                )
            )
        )
    }

    private fun offerDetailsObserver(): CustomObserverResponse<OfferDetails> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<OfferDetails> {

                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: OfferDetails?
                ) {
                    isFinished = data?.products?.isEmpty() == true
                    data?.products?.let {
                        adapter.addItems(it)
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


    private fun hideShowNoData() {
        if (adapter.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.constraintEmptyView?.visible()
        } else {
            binding?.layoutNoData?.constraintEmptyView?.gone()
            binding?.recyclerView?.visible()
        }
    }

    companion object {
        fun start(context: Context?, offerId: String) {
            val intent = Intent(context, OfferDetailsActivity::class.java).apply {
                putExtra(Constants.BundleData.OFFER_ID, offerId)
            }
            context?.startActivity(intent)
        }

    }

}