package com.raantech.mycups.ui.productdetails.fragments

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.databinding.FragmentOrderOfferSentBinding
import com.raantech.mycups.databinding.FragmentOrderSuccessBinding
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.ui.productdetails.viewmodels.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class OrderOfferSentFragment : BaseBindingFragment<FragmentOrderOfferSentBinding, Nothing>() {

    private val viewModel: ProductDetailsViewModel by activityViewModels()

    lateinit var callback: OnBackPressedCallback
    override fun getLayoutId(): Int = R.layout.fragment_order_offer_sent

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.thank_you
        )
        binding?.viewModel = viewModel
        setUpViewsListeners()
    }

    private fun setUpViewsListeners() {
        binding?.btnGoHome?.setOnClickListener {
            MainActivity.start(requireContext())
        }
        setUpOnBackPressed()
    }

    private fun setUpOnBackPressed() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MainActivity.start(requireContext())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}