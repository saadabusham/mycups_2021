package com.technzone.bai3.ui.main.fragments.home.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.LoginCallBack
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.enums.BannerTypesEnum
import com.technzone.bai3.data.models.category.Category
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.home.banner.Banner
import com.technzone.bai3.data.models.home.product.ProductFilter
import com.technzone.bai3.databinding.FragmentHomeBinding
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.base.bindingadapters.setOnItemClickListener
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.main.activity.MainActivity
import com.technzone.bai3.ui.main.fragments.home.adapters.BannerAdapter
import com.technzone.bai3.ui.main.fragments.home.adapters.BannerIndicatorRecyclerAdapter
import com.technzone.bai3.ui.main.fragments.home.adapters.CategoriesAdapter
import com.technzone.bai3.ui.main.fragments.home.viewmodels.HomeViewModel
import com.technzone.bai3.utils.extensions.connectToHomeIndicator
import com.technzone.bai3.utils.extensions.startSlider
import com.technzone.bai3.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(), LoginCallBack {

    private val viewModel: HomeViewModel by activityViewModels()

    var categoriesAdapter: CategoriesAdapter? = null
    var bannerAdapter: BannerAdapter? = null
    lateinit var indicatorRecyclerAdapter: BannerIndicatorRecyclerAdapter
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        setUpListeners()
        setUpRvCategories()
        setUpRvBanner()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        (requireActivity() as MainActivity).loginCallBack = this
    }

    private fun setUpRvCategories() {
        categoriesAdapter = CategoriesAdapter(requireContext())
        binding?.rvCatData?.adapter = categoriesAdapter
        binding?.rvCatData.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {
                item as Category
//                if(item.hasChild == false) {
//                    ProductsActivity.start(
//                        requireContext(),
//                        ProductFilter(categoryIds = if (item.id == null) null else item.id.toString())
//                    )
//                }else{
//                    SubCategoriesActivity.start(requireContext(),item)
//                }
            }
        })
        viewModel.getCategoriesData().observe(this, categoriesResultObserver())
    }

    private fun categoriesResultObserver(): CustomObserverResponse<ListWrapper<Category>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<ListWrapper<Category>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ListWrapper<Category>?
                ) {
                    data?.data?.let {
                        if (it.isEmpty())
                            return@let
                        categoriesAdapter?.submitItems(it)
                    }
                }

                override fun onError(subErrorCode: ResponseSubErrorsCodeEnum, message: String) {
                    super.onError(subErrorCode, message)
                    categoriesAdapter?.submitNewItems(
                        listOf(
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_see_all
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_see_all
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_see_all
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_see_all
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_see_all
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_see_all
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_see_all
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_see_all
                            )
                        )
                    )
                }
            }
        )
    }

    private fun setUpRvBanner() {
        bannerAdapter = BannerAdapter(requireContext())
        binding?.layoutBanner?.rvPagerData?.adapter = bannerAdapter
        binding?.layoutBanner?.rvPagerData.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {
                item as Banner
                when (item.type) {
                    BannerTypesEnum.PRODUCT.value -> {
//                        item.id?.let { ProductDetailsActivity.start(requireContext(), it) }
                    }
                    BannerTypesEnum.CATEGORY.value -> {
//                        ProductsActivity.start(
//                            requireContext(),
//                            ProductFilter(categoryIds = item.id.toString())
//                        )
                    }
                    BannerTypesEnum.DISCOUNT.value -> {
//                        ProductsActivity.start(requireContext(), ProductFilter(discountOnly = true))
                    }
                }
            }
        })
        indicatorRecyclerAdapter = BannerIndicatorRecyclerAdapter(requireContext())
        binding?.layoutBanner?.rvIndicator?.adapter = indicatorRecyclerAdapter
        viewModel.getBannerData().observe(this, bannerResultObserver())
    }

    private fun bannerResultObserver(): CustomObserverResponse<ListWrapper<Banner>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<ListWrapper<Banner>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ListWrapper<Banner>?
                ) {
                    data?.data?.let {
                        if (it.isEmpty())
                            return@let
                        bannerAdapter?.submitItems(it)
                        binding?.layoutBanner?.root?.visible()
                        binding?.layoutBanner?.rvIndicator?.let {
                            binding?.layoutBanner?.rvPagerData?.connectToHomeIndicator(
                                it
                            )
                            binding?.layoutBanner?.rvPagerData?.startSlider(period = 3000)
                        }
                    }
                }
            }
        )
    }
    override fun loggedInSuccess() {

    }
}