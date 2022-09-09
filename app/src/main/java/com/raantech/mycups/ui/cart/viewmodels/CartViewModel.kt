package com.raantech.mycups.ui.cart.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.enums.PaymentTypeEnum
import com.raantech.mycups.data.models.Price
import com.raantech.mycups.data.models.auth.login.Address
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderRequest
import com.raantech.mycups.data.repos.cart.CartRepo
import com.raantech.mycups.data.repos.orders.OrdersRepo
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val cartRepo: CartRepo,
    private val ordersRepo: OrdersRepo
) : BaseViewModel() {


    val cartCount: MutableLiveData<String> = MutableLiveData("0")
    val TAX_CONST: Double = 0.15
    var tax: MutableLiveData<Price> = MutableLiveData()
    var subTotal: MutableLiveData<Price> = MutableLiveData()
    var total: MutableLiveData<Price> = MutableLiveData()
    var paymentType: MutableLiveData<PaymentTypeEnum> =
        MutableLiveData(PaymentTypeEnum.ONLINE_PAYMENT)

    val orderId: MutableLiveData<String> = MutableLiveData()
    fun saveCart(accessory: Product) = viewModelScope.launch {
        cartRepo.saveCart(accessory)
    }

    fun deleteCart(id: Int) = viewModelScope.launch {
        cartRepo.deleteCart(id)
    }

    fun clearCart() = viewModelScope.launch {
        cartRepo.clearCart()
    }

    fun getCarts() = liveData {
        val cart = cartRepo.loadCarts()
        emit(cart)
    }

    fun getCart(id: Int) = liveData {
        val response = cartRepo.getCart(id)
        emit(response)
    }

    fun getCartsCount() = viewModelScope.launch {
        cartRepo.getCartsCount().observeForever {
            if (it != null)
                cartCount.postValue(it.toString())
        }
    }

    fun createPurchaseOrder(
        purchaseOrderRequest: PurchaseOrderRequest
    ) = liveData {
        emit(APIResource.loading())
        val response = ordersRepo.createPurchaseOrder(purchaseOrderRequest)
        emit(response)
    }

    fun hasUserAddress(): Boolean {
        return userRepo.getUser()?.user?.address != null &&
                userRepo.getUser()?.user?.address?.addressLat != null &&
                userRepo.getUser()?.user?.address?.addressLng != null
    }
    fun hasBrandName(): Boolean {
        return !userRepo.getUser()?.user?.brandName.isNullOrEmpty()
    }
}