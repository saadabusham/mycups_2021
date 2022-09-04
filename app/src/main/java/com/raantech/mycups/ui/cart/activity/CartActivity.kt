package com.raantech.mycups.ui.cart.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.OrderTypesEnum
import com.raantech.mycups.data.enums.PaymentTypeEnum
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderProducts
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderRequest
import com.raantech.mycups.databinding.ActivityCartBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.cart.fragments.cart.presenter.CartPresenter
import com.raantech.mycups.ui.cart.viewmodels.CartViewModel
import com.raantech.mycups.ui.offerdetails.adapters.OfferDetailsRecyclerAdapter
import com.raantech.mycups.ui.offerdetails.viewmodel.OfferDetailsViewModel
import com.raantech.mycups.utils.recycleviewutils.SwipeItemTouchCallBack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_storage.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class CartActivity : BaseBindingActivity<ActivityCartBinding, Nothing>() {

    lateinit var adapter: OfferDetailsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_cart,
            hasToolbar = false
        )
        setStartDestination()
    }

    private fun setStartDestination() {

        val navHostFragment = cart_nav_host_fragment as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.cart_nav_graph)
        graph.startDestination = R.id.cartFragment
        navHostFragment.navController.graph = graph
    }


    companion object {

        fun start(
            context: Context?
        ) {
            val intent = Intent(context, CartActivity::class.java)
            context?.startActivity(intent)
        }
    }
}