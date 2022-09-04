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
    }

    companion object {
        fun start(context: Context?, offerId: String) {
            val intent = Intent(context, OfferDetailsActivity::class.java).apply {
                putExtra(Constants.BundleData.OFFER_ID, offerId)
            }
            context?.startActivity(intent)
        }

    }

}