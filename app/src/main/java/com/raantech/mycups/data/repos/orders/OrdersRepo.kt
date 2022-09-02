package com.raantech.mycups.data.repos.orders

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.offer.OfferDetails
import com.raantech.mycups.data.models.orders.Order
import com.raantech.mycups.data.models.orders.OrderDetails
import com.raantech.mycups.data.models.orders.OrdersResponse
import com.raantech.mycups.data.models.orders.request.offerorder.OfferOrderRequest
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers


interface OrdersRepo {

    suspend fun createOfferOrder(
        orderRequest: OfferOrderRequest
    ): APIResource<ResponseWrapper<Int>>

    suspend fun createPurchaseOrder(
        orderRequest: PurchaseOrderRequest
    ): APIResource<ResponseWrapper<Int>>

    suspend fun getOrders(
        type: String?=null,
        name:String?=null
    ): APIResource<ResponseWrapper<OrdersResponse>>

    suspend fun getOrderDetails(
        id: Int
    ): APIResource<ResponseWrapper<OrderDetails>>

    suspend fun getOfferDetails(
        id: String
    ): APIResource<ResponseWrapper<OfferDetails>>

}