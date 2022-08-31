package com.raantech.mycups.ui.wishlist.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.wishlist.WishList
import com.raantech.mycups.databinding.ActivityWishlistBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.productdetails.activity.ProductDetailsActivity
import com.raantech.mycups.ui.wishlist.adapter.WishListRecyclerAdapter
import com.raantech.mycups.ui.wishlist.viewmodels.WishListViewModel
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class WishListActivity : BaseBindingActivity<ActivityWishlistBinding, Nothing>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: WishListViewModel by viewModels()
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false

    var positionToUpdate: Int = -1
    lateinit var wishListRecyclerAdapter: WishListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_wishlist,
            hasToolbar = true,
            toolbarView = toolbar,
            hasTitle = true,
            title = R.string.nav_favorites,
            hasBackButton = true,
            showBackArrow = true
        )
        setUpBinding()
        setUpListeners()
        setUpRecyclerView()
        loadData()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpRecyclerView() {
        wishListRecyclerAdapter = WishListRecyclerAdapter(this)
        binding?.recyclerView?.adapter = wishListRecyclerAdapter
        binding?.recyclerView?.setOnItemClickListener(this)
        Paginate.with(binding?.recyclerView, object : Paginate.Callbacks {
            override fun onLoadMore() {
                if (loading.value == false && wishListRecyclerAdapter.itemCount > 0 && !isFinished) {
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
    }

    private fun setUpListeners() {
    }

    private fun loadData() {
        viewModel.getWishList(
            wishListRecyclerAdapter.itemCount
        ).observe(this, wishlistObserver())
    }

    private fun wishlistObserver(): CustomObserverResponse<WishList> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<WishList> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<WishList>?
                ) {
                    isFinished = data?.body?.products?.isEmpty() == true
                    data?.body?.products?.let {
                        wishListRecyclerAdapter.addItems(it)
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
        if (wishListRecyclerAdapter.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.constraintEmptyView?.visible()
        } else {
            binding?.layoutNoData?.constraintEmptyView?.gone()
            binding?.recyclerView?.visible()
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
//                    wishListRecyclerAdapter.items[positionToUpdate].isWishlist =
//                        wishListRecyclerAdapter.items[positionToUpdate].isWishlist != true
//                    wishListRecyclerAdapter.notifyItemChanged(positionToUpdate)
//                    positionToUpdate = -1
                }
            }, false
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Product
        var isWishList = item.isWishlist == false
        var id = item.id ?: 0

        if (view?.id == R.id.imgFavorite) {
            positionToUpdate = position
            if (isWishList) {
                viewModel.removeFromWishList(id).observe(this, wishListActionObserver())
            } else {
                viewModel.addToWishList(
                    id
                ).observe(this, wishListActionObserver())
            }
        } else {
            if (item.is_fast == true) {
                item.id?.let { item.name?.let { it1 ->
                    ProductDetailsActivity.start(this, it,
                        it1
                    )
                } }
            }
        }
    }

    companion object {
        fun start(
            context: Context?
        ) {
            val intent = Intent(context, WishListActivity::class.java)
            context?.startActivity(intent)
        }
    }

}