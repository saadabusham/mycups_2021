package com.technzone.bai3.ui.main.fragments.buy.fragment

import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.LoginCallBack
import com.technzone.bai3.databinding.FragmentBuyBinding
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.main.activity.MainActivity
import com.technzone.bai3.ui.main.fragments.buy.viewmodels.BuyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyFragment : BaseBindingFragment<FragmentBuyBinding>(), LoginCallBack {

    private val viewModel: BuyViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_buy

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