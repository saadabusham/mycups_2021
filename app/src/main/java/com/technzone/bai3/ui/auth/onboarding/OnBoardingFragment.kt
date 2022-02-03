package com.technzone.bai3.ui.auth.onboarding

import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.technzone.bai3.R
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.data.models.auth.onboarding.OnBoardingItem
import com.technzone.bai3.databinding.FragmentOnBoardingBinding
import com.technzone.bai3.ui.auth.AuthViewModel
import com.technzone.bai3.ui.auth.onboarding.adapters.IndecatorRecyclerAdapter
import com.technzone.bai3.ui.auth.onboarding.adapters.OnBoardingAdapter
import com.technzone.bai3.ui.auth.register.RegisterActivity
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.base.views.appviews.UltraScaleTransformer
import com.technzone.bai3.ui.dataview.dataviewer.DataViewerActivity
import com.technzone.bai3.ui.main.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_on_boarding.*

@AndroidEntryPoint
class OnBoardingFragment : BaseBindingFragment<FragmentOnBoardingBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_on_boarding
    private val viewModel: AuthViewModel by activityViewModels()
    lateinit var indecatorRecyclerAdapter: IndecatorRecyclerAdapter
    private var indecatorPosition = 0
    lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onViewVisible() {
        super.onViewVisible()
        setUpListeners()
        setUpPager()
    }

    private fun setUpPager() {

        val items = arrayOf(
            OnBoardingItem(
                icon = R.drawable.ic_on_boarding_sample,
                title = R.string.title_on_boarding_1,
                body = R.string.description_on_boarding_1
            ),
            OnBoardingItem(
                icon = R.drawable.ic_on_boarding_sample,
                title = R.string.title_on_boarding_2,
                body = R.string.description_on_boarding_2
            ),
            OnBoardingItem(
                icon = R.drawable.ic_on_boarding_sample,
                title = R.string.title_on_boarding_3,
                body = R.string.description_on_boarding_3
            )
        )
        onBoardingAdapter =
            OnBoardingAdapter(requireContext()).apply { submitItems(items.toList()) }
        vpOnBoarding.adapter = onBoardingAdapter
        vpOnBoarding?.registerOnPageChangeCallback(pagerCallback)
        setUpIndicator()

    }


    private var pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateIndicator(position)
            if (position == 3) {
//                btnNextOnBoarding.visible()
            } else {
//                btnNextOnBoarding.invisible()
            }
        }
    }

    private fun setUpIndicator() {
        indecatorRecyclerAdapter = IndecatorRecyclerAdapter(requireContext())
        binding?.recyclerViewImagesDot?.adapter = indecatorRecyclerAdapter
        onBoardingAdapter.items.let {
            it.withIndex().forEach {
                indecatorRecyclerAdapter.submitItem(it.index == 0)
            }
        }
        binding?.vpOnBoarding?.registerOnPageChangeCallback(pagerCallback)
        binding?.vpOnBoarding?.setPageTransformer(UltraScaleTransformer())
    }

    private fun updateIndicator(position: Int) {
        indecatorRecyclerAdapter.items[indecatorPosition] = false
        indecatorRecyclerAdapter.items[position] = true
        indecatorRecyclerAdapter.notifyDataSetChanged()
        indecatorPosition = position
    }

    private fun setUpListeners() {
        binding?.ivClose?.setOnClickListener {
            MainActivity.start(requireContext())
        }
        binding?.btnGoogleLogin?.setOnClickListener {

        }
        binding?.btnEmailLogin?.setOnClickListener {
            navigationController.navigate(R.id.action_onBoardingFragment_to_loginFragment)
        }
        binding?.tvSignUp?.setOnClickListener {
            RegisterActivity.startForResult(
                requireActivity(), requireActivity().intent.getBooleanExtra(
                    Constants.BundleData.IS_ACTIVITY_RESULT,
                    false
                )
            )
        }
        binding?.tvTermsAndConditions?.setOnClickListener {
            DataViewerActivity.start(
                requireContext(),
                resources.getString(R.string.terms_and_conditions),
                viewModel.getTermsAndConditions()
            )
        }
        binding?.tvPrivacyPolicy?.setOnClickListener {
            DataViewerActivity.start(
                requireContext(),
                resources.getString(R.string.privacy_policy),
                viewModel.getPrivacyPolicy()
            )
        }
    }
}