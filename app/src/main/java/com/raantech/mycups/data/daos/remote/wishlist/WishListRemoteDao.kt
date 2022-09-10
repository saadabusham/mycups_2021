package com.raantech.mycups.data.daos.remote.wishlist

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.wishlist.WishList
import retrofit2.http.*

interface WishListRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("wish-lists")
    suspend fun getWishList(
    ): ResponseWrapper<WishList>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("wish-lists")
    suspend fun addToWishList(
        @Query("product_id") productId: Int
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @DELETE("wish-lists/{product_id}")
    suspend fun removeFromWishList(
        @Path("product_id") productId: Int
    ): ResponseWrapper<Any>

}