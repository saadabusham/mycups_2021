package com.raantech.mycups.data.repos.orders

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.remote.orders.OrdersRemoteDao
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.orders.Order
import com.raantech.mycups.data.models.orders.OrderDetails
import com.raantech.mycups.data.repos.base.BaseRepo
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