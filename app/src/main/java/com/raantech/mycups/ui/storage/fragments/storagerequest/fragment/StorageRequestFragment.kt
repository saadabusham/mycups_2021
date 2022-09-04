package com.raantech.mycups.ui.storage.fragments.storagerequest.fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.offer.OfferDetails
import com.raantech.mycups.data.models.home.product.productdetails.Measurement
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.storage.Storage
import com.raantech.mycups.data.models.storage.StorageResponse
import com.raantech.mycups.databinding.FragmentStorageBinding
import com.raantech.mycups.databinding.FragmentStorageRequestBinding
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.offerdetails.adapters.OfferDetailsRecyclerAdapter
import com.raantech.mycups.ui.storage.fragments.storage.adapters.StorageRecyclerAdapter
import com.raantech.mycups.ui.storage.fragments.storage.presenter.StoragePresenter
import com.raantech.mycups.ui.storage.fragments.storagerequest.adapters.StorageRequestRecyclerAdapter
import com.raantech.mycups.ui.storage.fragments.storagerequest.presenter.StorageRequestPresenter
import com.raantech.mycups.ui.storage.viewmodels.StorageViewModel
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class StorageRequestFragment :
    BaseBindingFragment<FragmentStorageRequestBinding, StorageRequestPresenter>(),
    StorageRequestPresenter {

    private val viewModel: StorageViewModel by activityViewModels()
    lateinit var adapter: StorageRequestRecyclerAdapter
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    override fun getLayoutId(): Int = R.layout.fragment_storage_request

    override fun getPresenter() = this
    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = resources.getString(R.string.storage)
        )
        binding?.viewModel = viewModel
        init()
    }

    private fun init() {
        setUpAdapter()
        loadData()
    }

    private fun loadData() {
        viewModel.getStorages()
            .observe(this, storageObserver())
    }

    override fun onRequestClicked() {

    }

    private fun setUpAdapter() {
        adapter = StorageRequestRecyclerAdapter(requireActivity())
        binding?.recyclerView?.adapter = adapter
        adapter.submitItems(
            arrayListOf(
                Storage(
                    measurement = Measurement(name = "2 onz"),
                    category = CategoriesItem(name = "Cups"),
                    quantity = 2000
                ),
                Storage(
                    measurement = Measurement(name = "4 onz"),
                    category = CategoriesItem(name = "Cups"),
                    quantity = 6000
                ),
                Storage(
                    measurement = Measurement(name = "6 onz"),
                    category = CategoriesItem(name = "Cups"),
                    quantity = 10000
                ),
                Storage(
                    measurement = Measurement(name = "2 okz"),
                    category = CategoriesItem(name = "Cups"),
                    quantity = 10000
                ),
                Storage(
                    measurement = Measurement(name = "2 okz"),
                    category = CategoriesItem(name = "Cups"),
                    quantity = 10000
                )
            )
        )
    }

    private fun storageObserver(): CustomObserverResponse<StorageResponse> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<StorageResponse> {

                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: StorageResponse?
                ) {
                    data?.storages?.let {
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

}