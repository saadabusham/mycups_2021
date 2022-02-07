package com.technzone.bai3.ui.more.faqs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.technzone.bai3.R
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.models.FaqsResponse
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.databinding.ActivityFaqsBinding
import com.technzone.bai3.ui.base.activity.BaseBindingActivity
import com.technzone.bai3.ui.more.faqs.adapter.FaqsRecyclerAdapter
import com.technzone.bai3.utils.extensions.gone
import com.technzone.bai3.utils.extensions.visible
import com.technzone.bai3.utils.plus
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FaqsActivity : BaseBindingActivity<ActivityFaqsBinding,Nothing>() {

    private val viewModel: FaqsViewModel by viewModels()
    lateinit var adapter: FaqsRecyclerAdapter
    private var originalList = ArrayList<FaqsResponse>()
    private var searchList = ArrayList<FaqsResponse>()

    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_faqs,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = resources.getString(R.string.more_faqs)
        )
        init()
    }

    private fun init() {
        setUpListeners()
        setUpAdapter()
        initSearch()
        viewModel.getFAQs().observe(this, faqsObserver())
    }

    private fun faqsObserver(): CustomObserverResponse<ListWrapper<FaqsResponse>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<ListWrapper<FaqsResponse>> {

                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ListWrapper<FaqsResponse>?
                ) {
                    if (data?.data.isNullOrEmpty()) {
                        binding?.layoutNoResult?.root?.visible()
                        return
                    }
                    originalList = data?.data as ArrayList<FaqsResponse>
                    adapter.submitItems(originalList)
                }
            })
    }


    private fun setUpAdapter() {
        adapter = FaqsRecyclerAdapter(this)
        binding?.rvHelpCenter?.adapter = adapter
    }

    private fun setUpListeners() {

    }

    private fun initSearch() {
        if (binding?.etSearch?.text?.isEmpty() == true) {
            adapter.submitItems(originalList)
        }
        disposable + binding?.etSearch?.textChangeEvents()
            ?.debounce(300, TimeUnit.MILLISECONDS)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe {
                if (it.text.isNotEmpty()) {
                    adapter.clear()
                    originalList.forEach { faqs ->
                        if (faqs.question.contains(it.text)) {
                            adapter.submitItem(faqs)
                        }
                    }
                    if (adapter.itemCount == 0) {
                        binding?.layoutNoResult?.root?.visible()
                    } else {
                        binding?.layoutNoResult?.root?.gone()
                    }
                } else {
                    binding?.layoutNoResult?.root?.gone()
                    adapter.submitItems(originalList)
                }
            }
    }

    companion object {

        fun start(context: Context?) {
            val intent = Intent(context, FaqsActivity::class.java)
            context?.startActivity(intent)
        }

    }

}