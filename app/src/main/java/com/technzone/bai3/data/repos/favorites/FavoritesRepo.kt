package com.technzone.bai3.data.repos.favorites

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.home.product.ProductFilter
import com.technzone.bai3.data.models.home.product.productdetails.Product


interface FavoritesRepo {

    suspend fun getFavorites(
        productFilter: ProductFilter
    ): APIResource<ResponseWrapper<ListWrapper<Product>>>

    suspend fun getFavoriteIds(
    ): APIResource<ResponseWrapper<List<Int>>>

    suspend fun addFavorite(
        id: Int
    ): APIResource<ResponseWrapper<Any>>

    suspend fun removeFavorite(
        id: Int
    ): APIResource<ResponseWrapper<Any>>

}