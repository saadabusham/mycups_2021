package com.raantech.mycups.data.repos.favorites

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.product.ProductFilter
import com.raantech.mycups.data.models.home.product.productdetails.Ads


interface FavoritesRepo {

    suspend fun getFavorites(
        productFilter: ProductFilter
    ): APIResource<ResponseWrapper<ListWrapper<Ads>>>

    suspend fun getFavoriteIds(
    ): APIResource<ResponseWrapper<List<Int>>>

    suspend fun addFavorite(
        id: Int
    ): APIResource<ResponseWrapper<Any>>

    suspend fun removeFavorite(
        id: Int
    ): APIResource<ResponseWrapper<Any>>

}