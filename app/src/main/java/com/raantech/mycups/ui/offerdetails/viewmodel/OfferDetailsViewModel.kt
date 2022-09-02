package com.raantech.mycups.ui.offerdetails.viewmodel

import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
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

    fun getOfferDetails(
        offerId: String
    ) = liveData {
        emit(APIResource.loading())
        val response =
            ordersRepo.getOfferDetails(offerId)
        emit(response)
    }

}