package com.technzone.bai3.ui.checkout.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.enums.DeliveryOptionsEnum
import com.technzone.bai3.data.enums.PaymentMethodEnum
import com.technzone.bai3.data.models.addresses.AddressList
import com.technzone.bai3.data.models.auth.login.UserDetailsResponseModel
import com.technzone.bai3.data.models.cart.CartPrice
import com.technzone.bai3.data.models.cart.CartProduct
import com.technzone.bai3.data.models.cart.CheckoutResponse
import com.technzone.bai3.data.models.cart.request.RemoveFromCartRequest
import com.technzone.bai3.data.models.promocode.PromoCode
import com.technzone.bai3.data.pref.cart.CartPref
import com.technzone.bai3.data.pref.favorite.FavoritePref
import com.technzone.bai3.data.repos.address.AddressRepo
import com.technzone.bai3.data.repos.cart.CartRepo
import com.technzone.bai3.data.repos.common.CommonRepo
import com.technzone.bai3.data.repos.configuration.ConfigurationRepo
import com.technzone.bai3.data.repos.favorites.FavoritesRepo
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val configurationRepo: ConfigurationRepo,
    private val favoritePref: FavoritePref,
    private val cartRepo: CartRepo,
    private val cartPref: CartPref,
    private val commonRepo: CommonRepo,
    private val addressRepo: AddressRepo
) : BaseViewModel() {
    val user: MutableLiveData<UserDetailsResponseModel> = MutableLiveData(userRepo.getUser())
    val contactNumber: MutableLiveData<String> =
        MutableLiveData(userRepo.getUser()?.phoneNumber)
    val selectedAddress: MutableLiveData<AddressList> = MutableLiveData()
    val searchText: MutableLiveData<String> = MutableLiveData("")
    var categories: List<Int> = mutableListOf()
    var pageNumber: Int = 1
    var promoCode: MutableLiveData<PromoCode> = MutableLiveData()
    var deliveryType: MutableLiveData<DeliveryOptionsEnum> =
        MutableLiveData(DeliveryOptionsEnum.HOME_DELIVERY)
    var paymentType: MutableLiveData<PaymentMethodEnum> =
        MutableLiveData(PaymentMethodEnum.CREDIT_CARD)
    val cartCount: MutableLiveData<Int> = MutableLiveData(0)
    val orderId: MutableLiveData<Int> = MutableLiveData(0)
    val deliveryDate: MutableLiveData<String> = MutableLiveData("")
    val cartPrice: MutableLiveData<CartPrice> = MutableLiveData(CartPrice())
    var cartListItems = listOf<CartProduct>()
    var hasDeliveryProduct: MutableLiveData<Boolean> = MutableLiveData(true)
    var hasCashProduct: MutableLiveData<Boolean> = MutableLiveData(true)

    //payment
    val checkoutId: MutableLiveData<String> = MutableLiveData()
    val paymentStatus: MutableLiveData<String> = MutableLiveData()
    var checkoutResponse: CheckoutResponse? = null
    fun getCart() = liveData {
        emit(APIResource.loading())
        val response = cartRepo.getCart()
        emit(response)
    }

    fun calculatePrice(addressId: Int, promoCode: Int?) = liveData {
        emit(APIResource.loading())
        val response = cartRepo.calculatePrice(addressId = addressId, promoCodeId = promoCode)
        emit(response)
    }

    fun applyPromoCode(promoCode: String) = liveData {
        emit(APIResource.loading())
        val response = cartRepo.applyPromoCode(promoCode)
        emit(response)
    }


    fun addProductToCart(
        id: Int,
        productSKUId: Int?
    ) = liveData {
        emit(APIResource.loading())
        val dealResponse = cartRepo.addProductToCart(id, productSKUId)
        emit(dealResponse)
    }


    fun minusProductFromCart(
        id: Int,
        productSKUId: Int?,
        forceDelete: Boolean = false
    ) = liveData {
        emit(APIResource.loading())
        val dealResponse = cartRepo.removeProductFromCart(
            RemoveFromCartRequest(
                productId = id,
                productSKUId = productSKUId,
                forceDelete = forceDelete
            )
        )
        emit(dealResponse)
    }

    fun checkout(
        paymentMethod: Int,
        addressId: Int,
        promoCodeId: Int? = null
    ) = liveData {
        emit(APIResource.loading())
        val dealResponse =
            cartRepo.checkOut(paymentMethod, addressId, promoCodeId, contactNumber.value.toString())
        emit(dealResponse)
    }

    fun getCartProductsIds(
    ) = liveData {
        emit(APIResource.loading())
        val response = cartRepo.getCartProductsIds()
        emit(response)
    }


    fun setCartList(list: List<Int>) {
        cartPref.setCartList(list)
    }

    fun addCart(id: Int) {
        cartPref.addCart(id)
    }

    fun removeCart(id: Int) {
        cartPref.removeCart(id)
    }

    fun getUserAddress(
    ) = liveData {
        emit(APIResource.loading())
        val response = addressRepo.getMyAddress()
        emit(response)
    }

    fun generateCheckoutId(
        amount: Double,
        currency: String,
        subscriptionId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response = cartRepo.generateCheckoutId(amount, currency, subscriptionId)
        emit(response)
    }

    fun getPaymentStatus(
        checkoutId: String
    ) = liveData {
        emit(APIResource.loading())
        val response = cartRepo.getPaymentStatus(
            checkoutId
        )
        emit(response)
    }
}