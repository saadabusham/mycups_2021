package com.raantech.mycups.ui.main.fragments.home.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.offer.Offer
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.databinding.FragmentHomeBinding
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.main.fragments.home.adapters.HomeCategoriesAdapter
import com.raantech.mycups.ui.main.fragments.home.presenter.HomePresenter
import com.raantech.mycups.ui.main.fragments.home.viewmodels.HomeViewModel
import com.raantech.mycups.ui.productdetails.activity.FastProductDetailsActivity
import com.raantech.mycups.ui.productdetails.activity.ProductDetailsActivity
import com.raantech.mycups.ui.subcategory.activity.SubcategoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomePresenter>(),
    HomePresenter {

    private val viewModel: HomeViewModel by activityViewModels()

    var homeCategoriesAdapter: HomeCategoriesAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getPresenter(): HomePresenter = this

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        setUpRvHomeCategories()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpRvHomeCategories() {
        homeCategoriesAdapter = HomeCategoriesAdapter(requireActivity())
        binding?.rvHomeCategories?.adapter = homeCategoriesAdapter
        binding?.rvHomeCategories.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {
                when (item) {
                    is CategoriesItem -> {
                        item.id?.let {
                            item.name?.let { it1 ->
                                SubcategoryActivity.start(
                                    requireContext(), it,
                                    it1
                                )
                            }
                        }
                    }
                    is DesignCategory -> {

                    }
                    is Offer -> {

                    }
                    is Product -> {
                        if (item.is_fast == true) {
                            item.id?.let {
                                FastProductDetailsActivity.start(
                                    requireContext(),
                                    it, item.name
                                )
                            }
                        } else {
                            item.id?.let {
                                ProductDetailsActivity.start(
                                    requireContext(),
                                    it, item.name
                                )
                            }
                        }
                    }
                }
            }
        })
        viewModel.loadHomeData().observe(this, homeCategoriesResultObserver())
    }

    private fun homeCategoriesResultObserver(): CustomObserverResponse<MutableList<Category>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<MutableList<Category>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: MutableList<Category>?
                ) {
                    data?.let {
                        if (it.isEmpty())
                            return@let
                        homeCategoriesAdapter?.submitItems(it)
                    }
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {

                }
            }
        )
    }

}