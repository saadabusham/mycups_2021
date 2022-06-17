package com.raantech.mycups.ui.main.fragments.home.fragment

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.common.interfaces.LoginCallBack
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.BannerTypesEnum
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.general.City
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.banner.Banner
import com.raantech.mycups.data.models.home.product.productdetails.Ads
import com.raantech.mycups.databinding.FragmentHomeBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.common.citypicker.activity.CitiesPickerActivity
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.ui.main.fragments.home.adapters.BannerAdapter
import com.raantech.mycups.ui.main.fragments.home.adapters.BannerIndicatorRecyclerAdapter
import com.raantech.mycups.ui.main.fragments.home.adapters.CategoriesAdapter
import com.raantech.mycups.ui.main.fragments.home.adapters.CategoriesItemsAdapter
import com.raantech.mycups.ui.main.fragments.home.presenter.HomePresenter
import com.raantech.mycups.ui.main.fragments.home.viewmodels.HomeViewModel
import com.raantech.mycups.utils.extensions.connectToHomeIndicator
import com.raantech.mycups.utils.extensions.startSlider
import com.raantech.mycups.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding,HomePresenter>(),HomePresenter, LoginCallBack {

    private val viewModel: HomeViewModel by activityViewModels()

    var categoriesAdapter: CategoriesAdapter? = null
    var categoriesItemsAdapter: CategoriesItemsAdapter? = null
    var bannerAdapter: BannerAdapter? = null
    lateinit var indicatorRecyclerAdapter: BannerIndicatorRecyclerAdapter

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getPresenter(): HomePresenter = this

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        setUpListeners()
        setUpRvCategories()
        setUpRvBanner()
        setUpRvCategoriesItems()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    override fun onSearchClicked() {

    }

    override fun onCityClicked() {
        CitiesPickerActivity.start(
            context = requireActivity(),
            currentCode = viewModel.selectedCity.value?.name,
            resultLauncher = citiesResultLauncher
        )
    }

    private fun setUpListeners() {
        (requireActivity() as MainActivity).loginCallBack = this
    }

    var citiesResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.selectedCity.value =
                    ((data?.getSerializableExtra(Constants.BundleData.CITY) as City))
            }
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
                                localIcon = R.drawable.ic_category_place_holder
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_place_holder
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_place_holder
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_place_holder
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_place_holder
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_place_holder
                            ),
                            Category(
                                id = null,
                                name = getString(R.string.see_all),
                                localIcon = R.drawable.ic_category_place_holder
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

                override fun onError(subErrorCode: ResponseSubErrorsCodeEnum, message: String) {
                    super.onError(subErrorCode, message)
                    bannerAdapter?.submitNewItems(
                        listOf(
                            Banner(),
                            Banner(),
                            Banner(),
                            Banner(),
                        )
                    )
                    binding?.layoutBanner?.root?.visible()
                    binding?.layoutBanner?.rvIndicator?.let {
                        binding?.layoutBanner?.rvPagerData?.connectToHomeIndicator(
                            it
                        )
                        binding?.layoutBanner?.rvPagerData?.startSlider(period = 3000)
                    }
                }
            }
        )
    }

    private fun setUpRvCategoriesItems() {
        categoriesItemsAdapter = CategoriesItemsAdapter(requireContext())
        binding?.rvCategoriesItems?.adapter = categoriesItemsAdapter
        binding?.rvCategoriesItems.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {

            }
        })
        viewModel.loadCategoriesProduct().observe(this, categoriesItemsResultObserver())
    }

    private fun categoriesItemsResultObserver(): CustomObserverResponse<ListWrapper<Category>> {
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
                        categoriesItemsAdapter?.submitItems(it)
                    }
                }

                override fun onError(subErrorCode: ResponseSubErrorsCodeEnum, message: String) {
                    super.onError(subErrorCode, message)
                    categoriesItemsAdapter?.submitNewItems(
                        listOf(
                            Category(),
                            Category(),
                            Category(),
                            Category(),
                            Category(),
                            Category()
                        )
                    )
                }
            }
        )
    }

    override fun loggedInSuccess() {

    }
}