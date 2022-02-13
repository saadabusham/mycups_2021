package com.raantech.mycups.ui.main.fragments.buy.fragment

import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.common.interfaces.LoginCallBack
import com.raantech.mycups.databinding.FragmentBuyBinding
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.ui.main.fragments.buy.presenter.BuyPresenter
import com.raantech.mycups.ui.main.fragments.buy.viewmodels.BuyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyFragment : BaseBindingFragment<FragmentBuyBinding, BuyPresenter>(),BuyPresenter, LoginCallBack {

    private val viewModel: BuyViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_buy

    override fun getPresenter(): BuyPresenter = this

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        setUpListeners()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        (requireActivity() as MainActivity).loginCallBack = this
    }

    override fun loggedInSuccess() {

    }
}