package com.raantech.mycups.data.daos.remote.cart

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.cart.Cart
import com.raantech.mycups.data.models.cart.CartPrice
import com.raantech.mycups.data.models.cart.CheckoutResponse
import com.raantech.mycups.data.models.cart.request.RemoveFromCartRequest
import com.raantech.mycups.data.models.promocode.PromoCode
import retrofit2.http.*

interface CartRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/cart")
    suspend fun getCart(
    ): ResponseWrapper<Cart>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/cart/product")
    suspend fun getCartProductsIds(
    ): ResponseWrapper<List<Int>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("api/cart/product")
    @FormUrlEncoded
    suspend fun addProductToCart(
        @Field("ProductId") productId: Int,
        @Field("ProductSKUId") productSKUId: Int?
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @HTTP(method = "DELETE", path = "api/cart/product", hasBody = true)
    suspend fun removeProductFromCart(
        @Body removeFromCartRequest: RemoveFromCartRequest
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @FormUrlEncoded
    @POST("api/cart/price")
    suspend fun calculatePrice(
        @Field("AddressId") addressId: Int,
        @Field("PromoCodeId") promoCodeId: Int?,
    ): ResponseWrapper<CartPrice>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @FormUrlEncoded
    @POST("api/cart/checkout")
    suspend fun checkOut(
        @Field("PaymentMethod") paymentMethod : Int,
        @Field("AddressId") addressId: Int?,
        @Field("PromoCodeId") promoCodeId: Int?,
        @Field("contactNumber") contactNumber: String,
    ): ResponseWrapper<CheckoutResponse>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/promoCode/{code}")
    suspend fun applyPromoCode(
        @Path("code") code : String,
    ): ResponseWrapper<PromoCode>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @FormUrlEncoded
    @POST("api/Payment/generateCheckoutId")
    suspend fun generateCheckoutId(
        @Field("Amount") amount: Double,
        @Field("Currency") currency: String,
        @Field("OrderId") orderId: Int
    ): ResponseWrapper<String>


    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @FormUrlEncoded
    @POST("api/payment/getPaymentStatus/{id}")
    suspend fun getPaymentStatus(
        @Path("id") id: String
    ): ResponseWrapper<Any>

}