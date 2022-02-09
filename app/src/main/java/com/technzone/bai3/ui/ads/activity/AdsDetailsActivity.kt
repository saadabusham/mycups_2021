package com.technzone.bai3.ui.ads.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.technzone.bai3.R
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.databinding.ActivityAdsDetailsBinding
import com.technzone.bai3.ui.ads.presenter.AdsDetailsPresenter
import com.technzone.bai3.ui.ads.viewmodels.AdsDetailsViewModel
import com.technzone.bai3.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdsDetailsActivity : BaseBindingActivity<ActivityAdsDetailsBinding, AdsDetailsPresenter>(),
    AdsDetailsPresenter {

    private val viewModel: AdsDetailsViewModel by viewModels()

    override fun getPresenter() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_ads_details,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = ""
        )
        setUpBinding()
        setUpListeners()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {

    }

    companion object {

        fun start(
            context: Context?,
            adsId: Int
        ) {
            val intent = Intent(context, AdsDetailsActivity::class.java).apply {
                putExtra(Constants.BundleData.ADS_ID, adsId)
            }
            context?.startActivity(intent)
        }
    }

}