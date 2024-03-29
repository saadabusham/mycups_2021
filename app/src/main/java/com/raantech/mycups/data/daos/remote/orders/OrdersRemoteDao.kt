package com.raantech.mycups.data.daos.remote.orders

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.offer.OfferDetails
import com.raantech.mycups.data.models.orders.Order
import com.raantech.mycups.data.models.orders.OrderDetails
import com.raantech.mycups.data.models.orders.OrderResponse
import com.raantech.mycups.data.models.orders.OrdersResponse
import com.raantech.mycups.data.models.orders.request.offerorder.OfferOrderRequest
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderRequest
import retrofit2.http.*

interface OrdersRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("orders")
    suspend fun createOfferOrder(
        @Body orderRequest: OfferOrderRequest
    ): ResponseWrapper<Int>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("orders")
    suspend fun createPurchaseOrder(
        @Body orderRequest: PurchaseOrderRequest
    ): ResponseWrapper<Int>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("orders")
    suspend fun getOrders(
        @Query("order_type") type: String?,
        @Query("query") name: String?
    ): ResponseWrapper<OrdersResponse>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("orders/{id}/show")
    suspend fun getOrderDetails(
        @Path("id") id: Int
    ): ResponseWrapper<OrderResponse>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("orders/{orderId}/offer/{offerId}/accept")
    suspend fun acceptOffer(
        @Path("orderId") orderId: Int,
        @Path("offerId") offerId: Int
    ): ResponseWrapper<Any>

}