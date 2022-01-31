package com.technzone.bai3.data.repos.orders

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseHandler
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.daos.remote.orders.OrdersRemoteDao
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.orders.Order
import com.technzone.bai3.data.models.orders.OrderDetails
import com.technzone.bai3.data.repos.base.BaseRepo
import com.technzone.bai3.data.repos.common.CommonRepo
import javax.inject.Inject

class OrdersRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val ordersRemoteDao: OrdersRemoteDao
) : BaseRepo(responseHandler), OrdersRepo {

    override suspend fun getOrders(
        pageSize: Int,
        pageNumber: Int,
        status: Int?
    ): APIResource<ResponseWrapper<ListWrapper<Order>>> {
        return try {
            responseHandle.handleSuccess(ordersRemoteDao.getOrders(pageSize, pageNumber, status))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getOrderDetails(id: Int): APIResource<ResponseWrapper<OrderDetails>> {
        return try {
            responseHandle.handleSuccess(ordersRemoteDao.getOrderDetails(id))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}