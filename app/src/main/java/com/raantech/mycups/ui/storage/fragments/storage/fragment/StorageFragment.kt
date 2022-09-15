package com.raantech.mycups.ui.storage.fragments.storage.fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.storage.StorageResponse
import com.raantech.mycups.databinding.FragmentStorageBinding
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.storage.fragments.storage.adapters.StorageRecyclerAdapter
import com.raantech.mycups.ui.storage.fragments.storage.presenter.StoragePresenter
import com.raantech.mycups.ui.storage.viewmodels.StorageViewModel
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class StorageFragment :
    BaseBindingFragment<FragmentStorageBinding, StoragePresenter>(),
    StoragePresenter {

    private val viewModel: StorageViewModel by activityViewModels()
    lateinit var adapter: StorageRecyclerAdapter
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    override fun getLayoutId(): Int = R.layout.fragment_storage
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

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun init() {
        setUpAdapter()
    }

    private fun loadData() {
        viewModel.getStorages()
            .observe(this, storageObserver())
    }

    override fun onRequestClicked() {
        navigationController.navigate(R.id.action_storageFragment_to_storageRequestFragment)
    }

    private fun setUpAdapter() {
        adapter = StorageRecyclerAdapter(requireActivity())
        binding?.recyclerView?.adapter = adapter
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
                        adapter.submitNewItems(it)
                    }
                    loading.value = (false)
                    hideShowNoData()
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    loading.value = (false)
                    hideShowNoData()
                }

                override fun onLoading() {
                    loading.value = (true)
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