package com.raantech.mycups.ui.offerdetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.models.orders.OrderDetails
import com.raantech.mycups.data.models.orders.OrderResponse
import com.raantech.mycups.data.repos.orders.OrdersRepo
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfferDetailsViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val ordersRepo: OrdersRepo
) : BaseViewModel() {
    val orderToView : MutableLiveData<OrderResponse> = MutableLiveData()
    fun getOrderDetails(
        orderId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            ordersRepo.getOrderDetails(orderId)
        emit(response)
    }

    fun acceptOffer(
        orderId: Int,
        offerId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            ordersRepo.acceptOffer(orderId = orderId, offerId = offerId)
        emit(response)
    }

}