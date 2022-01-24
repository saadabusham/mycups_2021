package com.technzone.bai3.data.repos.cart

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseHandler
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.daos.remote.cart.CartRemoteDao
import com.technzone.bai3.data.models.cart.Cart
import com.technzone.bai3.data.models.cart.CartPrice
import com.technzone.bai3.data.models.cart.CheckoutResponse
import com.technzone.bai3.data.models.cart.request.RemoveFromCartRequest
import com.technzone.bai3.data.repos.base.BaseRepo
import com.technzone.bai3.data.models.promocode.PromoCode
import javax.inject.Inject

class CartRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val cartRemoteDao: CartRemoteDao
) : BaseRepo(responseHandler), CartRepo {


    override suspend fun getCart(): APIResource<ResponseWrapper<Cart>> {
        return try {
            responseHandle.handleSuccess(
                cartRemoteDao.getCart(
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getCartProductsIds(): APIResource<ResponseWrapper<List<Int>>> {
        return try {
            responseHandle.handleSuccess(
                cartRemoteDao.getCartProductsIds()
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun addProductToCart(
        productId: Int,
        productSKUId: Int?
    ): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                cartRemoteDao.addProductToCart(productId, productSKUId)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun removeProductFromCart(
        removeFromCartRequest: RemoveFromCartRequest
    ): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                cartRemoteDao.removeProductFromCart(removeFromCartRequest)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun calculatePrice(
        addressId: Int,
        promoCodeId: Int?
    ): APIResource<ResponseWrapper<CartPrice>> {
        return try {
            responseHandle.handleSuccess(
                cartRemoteDao.calculatePrice(addressId, promoCodeId)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun checkOut(
        paymentMethod: Int,
        addressId: Int,
        promoCodeId: Int?,
        contactNumber: String
    ): APIResource<ResponseWrapper<CheckoutResponse>> {
        return try {
            responseHandle.handleSuccess(
                cartRemoteDao.checkOut(paymentMethod, addressId, promoCodeId,contactNumber)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun applyPromoCode(code: String): APIResource<ResponseWrapper<PromoCode>> {
        return try {
            responseHandle.handleSuccess(
                cartRemoteDao.applyPromoCode(code)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun generateCheckoutId(
        amount: Double,
        currency: String,
        orderId: Int
    ): APIResource<ResponseWrapper<String>> {
        return try {
            responseHandle.handleSuccess(
                cartRemoteDao.generateCheckoutId(
                    amount,
                    currency,
                    orderId
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getPaymentStatus(id: String): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                cartRemoteDao.getPaymentStatus(id)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }
}