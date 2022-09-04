package com.raantech.mycups.data.repos.cart

import androidx.lifecycle.LiveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.cart.Cart
import com.raantech.mycups.data.models.cart.CartPrice
import com.raantech.mycups.data.models.cart.CheckoutResponse
import com.raantech.mycups.data.models.cart.request.RemoveFromCartRequest
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.promocode.PromoCode

interface CartRepo {

    suspend fun saveCart(accessory: Product)
    suspend fun saveCarts(accessories: List<Product>)
    suspend fun loadCarts(): List<Product>
    suspend fun getCart(id: Int): Product
    fun getCartsCount(): LiveData<Int>
    suspend fun getCartsCountInt(): Int?
    suspend fun deleteCart(id:Int)
    suspend fun clearCart()
}