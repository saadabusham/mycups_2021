package com.raantech.mycups.data.repos.product

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.product.productdetails.Ads

interface ProductRepo {

    suspend fun getProductById(
        productId: Int?
    ): APIResource<ResponseWrapper<Ads>>

    suspend fun getProductsList(
        fields: Map<String, String>,
    ): APIResource<ResponseWrapper<ListWrapper<Ads>>>

}