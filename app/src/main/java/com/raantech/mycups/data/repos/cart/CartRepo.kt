package com.raantech.mycups.data.repos.cart

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.cart.Cart
import com.raantech.mycups.data.models.cart.CartPrice
import com.raantech.mycups.data.models.cart.CheckoutResponse
import com.raantech.mycups.data.models.cart.request.RemoveFromCartRequest
import com.raantech.mycups.data.models.promocode.PromoCode

interface CartRepo {

    suspend fun getCart(
    ): APIResource<ResponseWrapper<Cart>>


    suspend fun getCartProductsIds(
    ): APIResource<ResponseWrapper<List<Int>>>

    suspend fun addProductToCart(
        productId: Int,
        productSKUId: Int?=null
    ): APIResource<ResponseWrapper<Any>>

    suspend fun removeProductFromCart(
        removeFromCartRequest: RemoveFromCartRequest
    ): APIResource<ResponseWrapper<Any>>

    suspend fun calculatePrice(
        addressId: Int,
        promoCodeId: Int? = null,
    ): APIResource<ResponseWrapper<CartPrice>>

    suspend fun checkOut(
        paymentMethod: Int,
        addressId: Int,
        promoCodeId: Int? = null,
        contactNumber: String
    ): APIResource<ResponseWrapper<CheckoutResponse>>

    suspend fun applyPromoCode(
        code: String,
    ): APIResource<ResponseWrapper<PromoCode>>

    suspend fun generateCheckoutId(
        amount: Double,
        currency: String,
        orderId: Int
    ): APIResource<ResponseWrapper<String>>

    suspend fun getPaymentStatus(
        id: String
    ): APIResource<ResponseWrapper<Any>>

}