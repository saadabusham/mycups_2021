package com.raantech.mycups.data.repos.product

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.home.product.productdetails.ProductResponse
import com.raantech.mycups.data.models.home.product.productdetails.ProductsResponse
import retrofit2.http.Path

interface ProductRepo {

    suspend fun getProductById(
        productId: Int?
    ): APIResource<ResponseWrapper<ProductResponse>>

    suspend fun getProductsList(
        categoryId:Int,
    ): APIResource<ResponseWrapper<ProductsResponse>>

    suspend fun searchProductsList(
        query: String?
    ): APIResource<ResponseWrapper<ProductsResponse>>
}