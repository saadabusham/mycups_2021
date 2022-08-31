package com.raantech.mycups.ui.more.orders.viewmodels

import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.repos.orders.OrdersRepo
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val ordersRepo: OrdersRepo
) : BaseViewModel() {

    fun getOrders(
        type:String? = null,
        name:String? = null
    ) = liveData {
        emit(APIResource.loading())
        val response = ordersRepo.getOrders(type = type , name = name)
        emit(response)
    }
    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn
}