package com.technzone.bai3.ui.auth.onboarding

import androidx.viewpager2.widget.ViewPager2
import com.technzone.bai3.R
import com.technzone.bai3.databinding.FragmentOnBoardingBinding
import com.technzone.bai3.ui.auth.onboarding.adapters.IndecatorRecyclerAdapter
import com.technzone.bai3.data.models.auth.onboarding.OnBoardingItem
import com.technzone.bai3.ui.auth.onboarding.adapters.OnBoardingAdapter
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.utils.extensions.invisible
import com.technzone.bai3.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_on_boarding.*

@AndroidEntryPoint
class OnBoardingFragment : BaseBindingFragment<FragmentOnBoardingBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_on_boarding
    lateinit var indecatorRecyclerAdapter: IndecatorRecyclerAdapter
    private var indecatorPosition = 0
    override fun onViewVisible() {
        super.onViewVisible()
        setUpListeners()
        setUpPager()
    }

    private fun setUpPager() {

        val items = arrayOf(
            OnBoardingItem(
                title = R.string.title_on_boarding_1,
                body = R.string.description_on_boarding_1
            ),
            OnBoardingItem(
                title = R.string.title_on_boarding_2,
                body = R.string.description_on_boarding_2
            ),
            OnBoardingItem(
                title = R.string.title_on_boarding_3,
                body = R.string.description_on_boarding_3
            ),
            OnBoardingItem(
                title = R.string.title_on_boarding_4,
                body = R.string.description_on_boarding_4
            )
        )
        vpOnBoarding.adapter =
            OnBoardingAdapter(requireContext()).apply { submitItems(items.toList()) }
        vpOnBoarding?.registerOnPageChangeCallback(pagerCallback)

    }


    private var pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateIndicator(position)
            if (position == 3) {
                btnNextOnBoarding.visible()
            } else {
                btnNextOnBoarding.invisible()
            }
        }
    }

    private fun updateIndicator(position: Int) {
        indecatorRecyclerAdapter.items[indecatorPosition] = false
        indecatorRecyclerAdapter.items[position] = true
        indecatorRecyclerAdapter.notifyDataSetChanged()
        indecatorPosition = position
    }

    private fun setUpListeners() {
//        binding?.ivClose?.setOnClickListener {
//            navigationController.navigate(R.id.action_onBoardingFragment_to_loginFragment)
//        }
//        binding?.btnNextOnBoarding?.setOnClickListener {
//            navigationController.navigate(R.id.action_onBoardingFragment_to_loginFragment)
//        }
    }
}