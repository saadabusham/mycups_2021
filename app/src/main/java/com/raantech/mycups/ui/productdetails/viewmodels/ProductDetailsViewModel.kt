package com.raantech.mycups.ui.productdetails.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.RequestStatusEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.media.Media
import com.raantech.mycups.data.models.orders.request.offerorder.OfferOrderRequest
import com.raantech.mycups.data.models.orders.request.purchaseorder.PurchaseOrderRequest
import com.raantech.mycups.data.pref.configuration.ConfigurationPref
import com.raantech.mycups.data.pref.user.UserPref
import com.raantech.mycups.data.repos.common.CommonRepo
import com.raantech.mycups.data.repos.configuration.ConfigurationRepo
import com.raantech.mycups.data.repos.orders.OrdersRepo
import com.raantech.mycups.data.repos.product.ProductRepo
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.IDN
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val commonRepo: CommonRepo,
    private val userRepo: UserRepo,
    private val productRepo: ProductRepo,
    private val ordersRepo: OrdersRepo
) : BaseViewModel() {
    val design: MutableLiveData<Media> = MutableLiveData()
    val productToView: MutableLiveData<Product> = MutableLiveData()
    val count: MutableLiveData<Int> = MutableLiveData(0)
    val orderId: MutableLiveData<String> = MutableLiveData()
    val needStock: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getProductsByID(
        productId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response = productRepo.getProductById(productId)
        emit(response)
    }

    fun createPurchaseOrder(
        purchaseOrderRequest: PurchaseOrderRequest
    ) = liveData {
        emit(APIResource.loading())
        val response = ordersRepo.createPurchaseOrder(purchaseOrderRequest)
        emit(response)
    }

    fun createOfferOrder(
        offerOrderRequest: OfferOrderRequest
    ) = liveData {
        emit(APIResource.loading())
        val response = ordersRepo.createOfferOrder(offerOrderRequest)
        emit(response)
    }

    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn
}