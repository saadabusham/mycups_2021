package com.raantech.mycups.ui.offerdetails.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.raantech.mycups.R
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.databinding.ActivityOfferDetailsBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_offer_details.*
import kotlinx.android.synthetic.main.activity_product_details.*

@AndroidEntryPoint
class OfferDetailsActivity :
    BaseBindingActivity<ActivityOfferDetailsBinding, Nothing>() {

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
        setStartDestination()
    }

    private fun setStartDestination() {

        val navHostFragment = offer_nav_host_fragment as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.offer_nav_graph)

        graph.startDestination = R.id.offerDetailsFragment
        navHostFragment.navController.graph = graph
    }

    companion object {
        fun start(context: Context?, orderId: Int) {
            val intent = Intent(context, OfferDetailsActivity::class.java).apply {
                putExtra(Constants.BundleData.ORDER_ID, orderId)
            }
            context?.startActivity(intent)
        }

    }

}