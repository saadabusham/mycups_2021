package com.technzone.bai3.data.daos.remote.orders

import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.common.NetworkConstants
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.orders.Order
import com.technzone.bai3.data.models.orders.OrderDetails
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface OrdersRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/order")
    suspend fun getOrders(
            @Query("PageSize") pageSize: Int,
            @Query("PageNumber") pageNumber: Int,
            @Query("Status") status: Int?
    ): ResponseWrapper<ListWrapper<Order>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/order/{id}")
    suspend fun getOrderDetails(
            @Path("id") id: Int
    ): ResponseWrapper<OrderDetails>

}