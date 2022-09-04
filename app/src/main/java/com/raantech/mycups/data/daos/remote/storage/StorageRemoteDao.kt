package com.raantech.mycups.data.daos.remote.storage

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.offer.OfferDetails
import com.raantech.mycups.data.models.orders.Order
import com.raantech.mycups.data.models.orders.OrderDetails
import com.raantech.mycups.data.models.orders.OrdersResponse
import com.raantech.mycups.data.models.orders.request.offerorder.OfferOrderRequest
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderRequest
import com.raantech.mycups.data.models.storage.StorageResponse
import retrofit2.http.*

interface StorageRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("storage")
    suspend fun getStorages(
    ): ResponseWrapper<StorageResponse>

}