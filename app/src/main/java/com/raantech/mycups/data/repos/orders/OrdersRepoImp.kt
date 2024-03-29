package com.raantech.mycups.data.repos.orders

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.remote.orders.OrdersRemoteDao
import com.raantech.mycups.data.models.home.offer.OfferDetails
import com.raantech.mycups.data.models.orders.OrderDetails
import com.raantech.mycups.data.models.orders.OrderResponse
import com.raantech.mycups.data.models.orders.OrdersResponse
import com.raantech.mycups.data.models.orders.request.offerorder.OfferOrderRequest
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderRequest
import com.raantech.mycups.data.repos.base.BaseRepo
import javax.inject.Inject

class OrdersRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val ordersRemoteDao: OrdersRemoteDao
) : BaseRepo(responseHandler), OrdersRepo {

    override suspend fun createOfferOrder(orderRequest: OfferOrderRequest): APIResource<ResponseWrapper<Int>> {
        return try {
            responseHandle.handleSuccess(ordersRemoteDao.createOfferOrder(orderRequest))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun createPurchaseOrder(orderRequest: PurchaseOrderRequest): APIResource<ResponseWrapper<Int>> {
        return try {
            responseHandle.handleSuccess(ordersRemoteDao.createPurchaseOrder(orderRequest))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getOrders(
        type: String?,
        name: String?
    ): APIResource<ResponseWrapper<OrdersResponse>> {
        return try {
            responseHandle.handleSuccess(ordersRemoteDao.getOrders(type, name))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getOrderDetails(id: Int): APIResource<ResponseWrapper<OrderResponse>> {
        return try {
            responseHandle.handleSuccess(ordersRemoteDao.getOrderDetails(id))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun acceptOffer(
        orderId: Int,
        offerId: Int
    ): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(ordersRemoteDao.acceptOffer(orderId, offerId))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }
}