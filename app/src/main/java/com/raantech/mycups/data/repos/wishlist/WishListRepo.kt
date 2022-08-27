package com.raantech.mycups.data.repos.wishlist

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.wishlist.WishList

interface WishListRepo {

    suspend fun getWishList(
            skip: Int
    ): APIResource<ResponseWrapper<WishList>>

    suspend fun addToWishList(
        productId: Int
    ): APIResource<ResponseWrapper<Any>>

    suspend fun removeFromWishList(
            productId: Int
    ): APIResource<ResponseWrapper<Any>>

}