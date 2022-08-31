package com.raantech.mycups.ui.productdetails.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.raantech.mycups.R
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.databinding.ActivityProductDetailsBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_product_details.*

@AndroidEntryPoint
class ProductDetailsActivity :
    BaseBindingActivity<ActivityProductDetailsBinding, Nothing>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_product_details,
            hasToolbar = false
        )
        setStartDestination()
    }

    private fun setStartDestination() {

        val navHostFragment = product_details_nav_host_fragment as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.product_details_nav_graph)

        if (intent.getBooleanExtra(Constants.BundleData.IS_FAST, false)) {
            graph.startDestination = R.id.fastProductDetailsFragment
        } else {
            graph.startDestination = R.id.productDetailsFragment
        }

        navHostFragment.navController.graph = graph
    }

    companion object {

        fun start(
            context: Context?,
            productId: Int,
            productName: String?,
            isFast: Boolean? = false
        ) {
            val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                putExtra(Constants.BundleData.PRODUCT_ID, productId)
                putExtra(Constants.BundleData.PRODUCT_NAME, productName ?: "")
                putExtra(Constants.BundleData.IS_FAST, isFast)
            }
            context?.startActivity(intent)
        }
    }

}