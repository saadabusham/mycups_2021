package com.raantech.mycups.data.daos.remote.product

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.product.productdetails.Ads
import retrofit2.http.*

interface ProductRemoteDao {

    @GET("product/{id}")
    suspend fun getProductById(
        @Path("id") productId: Int?
    ): ResponseWrapper<Ads>

    @FormUrlEncoded
    @POST("product/search")
    suspend fun getProductsList(
        @FieldMap fields: Map<String, String>,
    ): ResponseWrapper<ListWrapper<Ads>>

}