package com.technzone.bai3.data.daos.remote.product

import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.home.product.productdetails.Product
import retrofit2.http.*

interface ProductRemoteDao {

    @GET("product/{id}")
    suspend fun getProductById(
        @Path("id") productId: Int?
    ): ResponseWrapper<Product>

    @FormUrlEncoded
    @POST("product/search")
    suspend fun getProductsList(
        @FieldMap fields: Map<String, String>,
    ): ResponseWrapper<ListWrapper<Product>>

}