package com.raantech.mycups.data.daos.remote.favorites

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.product.ProductFilter
import com.raantech.mycups.data.models.home.product.productdetails.Ads
import retrofit2.http.*

interface FavoritesRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("api/product/search")
    suspend fun getFavorites(
            @Body productFilter: ProductFilter
    ): ResponseWrapper<ListWrapper<Ads>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/user/product/favorite")
    suspend fun getFavoriteIds(
    ): ResponseWrapper<List<Int>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("api/user/product/favorite/{id}")
    suspend fun addFavorite(
            @Path("id") id: Int
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @DELETE("api/user/product/favorite/{id}")
    suspend fun removeFavorite(
            @Path("id") id: Int
    ): ResponseWrapper<Any>

}