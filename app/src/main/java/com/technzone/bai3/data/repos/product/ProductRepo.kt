package com.technzone.bai3.data.repos.product

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.home.product.productdetails.Product

interface ProductRepo {

    suspend fun getProductById(
        productId: Int?
    ): APIResource<ResponseWrapper<Product>>

    suspend fun getProductsList(
        fields: Map<String, String>,
    ): APIResource<ResponseWrapper<ListWrapper<Product>>>

}