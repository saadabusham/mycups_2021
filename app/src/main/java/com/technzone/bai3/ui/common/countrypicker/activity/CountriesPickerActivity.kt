package com.technzone.bai3.ui.common.countrypicker.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.technzone.bai3.R
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.models.general.Countries
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.databinding.ActivityCountriesBinding
import com.technzone.bai3.ui.base.activity.BaseBindingActivity
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.base.bindingadapters.setOnItemClickListener
import com.technzone.bai3.ui.common.countrypicker.adapter.CountriesRecyclerAdapter
import com.technzone.bai3.ui.common.countrypicker.viewmodels.CountriesViewModel
import com.technzone.bai3.utils.extensions.gone
import com.technzone.bai3.utils.extensions.setupClearButtonWithAction
import com.technzone.bai3.utils.extensions.showErrorAlert
import com.technzone.bai3.utils.extensions.visible
import com.technzone.bai3.utils.plus
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CountriesPickerActivity : BaseBindingActivity<ActivityCountriesBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: CountriesViewModel by viewModels()

    private lateinit var countriesAdapter: CountriesRecyclerAdapter
    private val originalList: MutableList<Countries> = mutableListOf()
    var compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_countries,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.select_countries,
            navigationIcon = R.drawable.ic_close,
            backArrowTint = R.color.black
        )
        observeLoading()
        setUpRecyclerView()
        setUpListeners()
        initSearch()
    }

    private fun setUpListeners() {
    }

    private fun onDone() {
        val selectedCountry = countriesAdapter.getSelectedItem()
        if (selectedCountry == null) {
            showErrorAlert(
                message = getString(R.string.please_select_country)
            )
            return
        }
        val data = Intent()
        data.putExtra(
            Constants.BundleData.COUNTRY,
            selectedCountry
        )
        setResult(RESULT_OK, data)
        finish()
    }

    private fun setUpRecyclerView() {
        countriesAdapter = CountriesRecyclerAdapter(
            this,
            intent.getBooleanExtra(Constants.BundleData.SHOW_CODE, true)
        )
        binding?.recyclerView?.adapter = countriesAdapter
        binding?.recyclerView?.setOnItemClickListener(this)
//        val localData: List<Countries> =
//            readRawJson(this, R.raw.countries)
//        countriesAdapter.submitItems(localData)
//        localData.singleOrNull { it.code == intent.getStringExtra(Constants.BundleData.CURRENT_COUNTRY) }
//            ?.apply {
//                selected = true
//            }
//        originalList.addAll(localData)
        loadData()
    }

    private fun initSearch() {
        binding?.edTitle?.setupClearButtonWithAction()
        compositeDisposable + binding?.edTitle?.textChangeEvents()
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
        countriesAdapter.clear()
        page = 1
        viewModel.textToSearch = text
        loadData()
    }

    private fun loadData() {
        viewModel.getCountriesCode(pageNumber = page, pageSize = Constants.PAGE_SIZE)
            .observe(this, countriesCodeResultObserver())
    }


    private fun countriesCodeResultObserver(): CustomObserverResponse<ListWrapper<Countries>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<ListWrapper<Countries>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ListWrapper<Countries>?
                ) {
                    isFinished =
                        data?.data?.size?.plus(countriesAdapter.itemCount) ?: 0 >= data?.totalRows ?: 0

                    data?.data?.let {
                        if (page == 1) {
                            countriesAdapter.submitNewItems(it)
                        } else {
                            countriesAdapter.addItems(it)
                        }
                    }
                    loading.postValue(false)
                    hideShowNoData()
                }

                override fun onError(subErrorCode: ResponseSubErrorsCodeEnum, message: String) {
                    super.onError(subErrorCode, message)
                    loading.postValue(false)
                    hideShowNoData()
                }

                override fun onLoading() {
                    super.onLoading()
                    loading.postValue(true)
                }
            }, withProgress = false
        )
    }

    private fun observeLoading() {
        loading.observe(this, {
            if (it) {
                binding?.layoutShimmer?.shimmerViewContainer?.visible()
                binding?.layoutShimmer?.shimmerViewContainer?.startShimmer()
            } else {
                binding?.layoutShimmer?.shimmerViewContainer?.gone()
                binding?.layoutShimmer?.shimmerViewContainer?.stopShimmer()
            }
        })
    }

    private fun hideShowNoData() {
        countriesAdapter.itemCount.let {
            if (it == 0) {
                binding?.layoutNoResult?.root?.visible()
            } else {
                binding?.layoutNoResult?.root?.gone()
            }
        }
    }

    companion object {
        fun start(
            context: Context?
        ) {
            val intent = Intent(context, CountriesPickerActivity::class.java)
            context?.startActivity(intent)
        }

        fun start(
            context: Activity?,
            currentCode: String? = "",
            resultLauncher: ActivityResultLauncher<Intent>,
            showCode: Boolean = true
        ) {
            val intent = Intent(context, CountriesPickerActivity::class.java).apply {
                putExtra(Constants.BundleData.CURRENT_COUNTRY, currentCode)
                putExtra(Constants.BundleData.SHOW_CODE, showCode)
            }
            resultLauncher.launch(intent)
        }
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        onDone()
    }
}