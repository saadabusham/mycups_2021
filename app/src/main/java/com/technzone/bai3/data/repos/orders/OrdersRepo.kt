package com.technzone.bai3.data.repos.orders

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.orders.Order
import com.technzone.bai3.data.models.orders.OrderDetails


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