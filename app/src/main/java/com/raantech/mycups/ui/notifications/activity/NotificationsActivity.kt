package com.raantech.mycups.ui.notifications.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.notification.Notification
import com.raantech.mycups.databinding.ActivityNotificationsBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.notifications.adapters.NotificationsRecyclerAdapter
import com.raantech.mycups.ui.notifications.viewmodel.NotificationsViewModel
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsActivity : BaseBindingActivity<ActivityNotificationsBinding,Nothing>() {

    private val viewModel: NotificationsViewModel by viewModels()
    lateinit var adapter: NotificationsRecyclerAdapter
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_notifications,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = resources.getString(R.string.menu_notifications)
        )
        init()
    }

    private fun init() {
        setUpListeners()
        setUpAdapter()
        loadData()
    }

    private fun loadData() {
        viewModel.getNotifications(adapter.itemCount).observe(this, notificationsObserver())
    }

    private fun setUpListeners() {

    }

    private fun setUpAdapter() {
        adapter = NotificationsRecyclerAdapter(this)
        binding?.recyclerView?.adapter = adapter
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
                        Notification(
                                title = "تم ارسال تسعير للطلب رقم #3321",
                                read = true
                        ),
                        Notification(
                                title = "لقد تم اضافة منتجات جديدة تصفحها الان",
                                read = true
                        ),
                        Notification(
                                title = "نود تذكيرك بان لديك في مخازننا اكواب عدد 10000",
                                read = true
                        ),
                        Notification(
                                title = "لقد تم توصيل منتجاتك الان الى المخزن"
                        )
                )
        )
    }

    private fun notificationsObserver(): CustomObserverResponse<List<Notification>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<List<Notification>> {

                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: List<Notification>?
                ) {
                    isFinished = data?.isNullOrEmpty() == true
                    data?.let {
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

        fun start(context: Context?) {
            val intent = Intent(context, NotificationsActivity::class.java)
            context?.startActivity(intent)
        }

    }

}