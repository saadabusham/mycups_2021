package com.raantech.mycups.data.daos.remote.product

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.home.product.productdetails.ProductResponse
import com.raantech.mycups.data.models.home.product.productdetails.ProductsResponse
import retrofit2.http.*

interface ProductRemoteDao {

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") productId: Int?
    ): ResponseWrapper<ProductResponse>

    @GET("category/{categoryId}/products")
    suspend fun getProductsList(
        @Path("categoryId") categoryId: Int
    ): ResponseWrapper<ProductsResponse>

}