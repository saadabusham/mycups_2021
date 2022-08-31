package com.raantech.mycups.ui.more.orders.activtiy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.raantech.mycups.R
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.databinding.ActivityOrdersBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_orders.*

@AndroidEntryPoint
class OrdersActivity :
    BaseBindingActivity<ActivityOrdersBinding, Nothing>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_orders,
            hasToolbar = false
        )
        setStartDestination()
    }

    private fun setStartDestination() {

        val navHostFragment = orders_nav_host_fragment as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.orders_nav_graph)

        if (intent.getBooleanExtra(Constants.BundleData.IS_LISTING, true)) {
            graph.startDestination = R.id.ordersFragment
        } else {
//            graph.startDestination = R.id.productDetailsFragment
        }

        navHostFragment.navController.graph = graph
    }

    companion object {

        fun start(
            context: Context?,
            orderId: Int? = null,
            isListing: Boolean? = true
        ) {
            val intent = Intent(context, OrdersActivity::class.java).apply {
                putExtra(Constants.BundleData.ORDER_ID, orderId)
                putExtra(Constants.BundleData.IS_LISTING, isListing)
            }
            context?.startActivity(intent)
        }
    }

}