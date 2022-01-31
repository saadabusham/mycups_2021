package com.technzone.bai3.ui.main.fragments.home.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.LoginCallBack
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.models.category.Category
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.databinding.FragmentHomeBinding
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.base.bindingadapters.setOnItemClickListener
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.main.activity.MainActivity
import com.technzone.bai3.ui.main.fragments.home.adapters.CategoriesAdapter
import com.technzone.bai3.ui.main.fragments.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(), LoginCallBack {

    private val viewModel: HomeViewModel by activityViewModels()

    var categoriesAdapter: CategoriesAdapter? = null
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        setUpListeners()
        setUpRvCategories()
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

    override fun loggedInSuccess() {

    }
}