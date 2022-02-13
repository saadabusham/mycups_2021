package com.raantech.mycups.data.repos.orders

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.orders.Order
import com.raantech.mycups.data.models.orders.OrderDetails


interface OrdersRepo {

    suspend fun getOrders(
        pageSize: Int,
        pageNumber: Int,
        status: Int?
    ): APIResource<ResponseWrapper<ListWrapper<Order>>>

    suspend fun getOrderDetails(
        id: Int
    ): APIResource<ResponseWrapper<OrderDetails>>

}