package com.technzone.bai3.ui.main.fragments.home.fragment

import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.LoginCallBack
import com.technzone.bai3.databinding.FragmentHomeBinding
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.main.activity.MainActivity
import com.technzone.bai3.ui.main.fragments.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(), LoginCallBack {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_home

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