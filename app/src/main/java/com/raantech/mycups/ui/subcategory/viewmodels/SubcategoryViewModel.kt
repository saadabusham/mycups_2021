package com.raantech.mycups.ui.subcategory.viewmodels

import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.repos.common.CommonRepo
import com.raantech.mycups.data.repos.product.ProductRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubcategoryViewModel @Inject constructor(
    private val commonRepo: CommonRepo,
    private val productRepo: ProductRepo
) : BaseViewModel() {

    fun getSubCategories(
        categoryId:Int
    ) = liveData {
        emit(APIResource.loading())
        val response = commonRepo.getSubCategories(categoryId)
        emit(response)
    }
    fun getProducts(
        categoryId:Int
    ) = liveData {
        emit(APIResource.loading())
        val response = productRepo.getProductsList(categoryId)
        emit(response)
    }
}