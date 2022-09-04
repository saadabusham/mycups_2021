package com.raantech.mycups.data.repos.cart

import androidx.lifecycle.LiveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.local.CartLocalDao
import com.raantech.mycups.data.daos.remote.cart.CartRemoteDao
import com.raantech.mycups.data.models.cart.Cart
import com.raantech.mycups.data.models.cart.CartPrice
import com.raantech.mycups.data.models.cart.CheckoutResponse
import com.raantech.mycups.data.models.cart.request.RemoveFromCartRequest
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.repos.base.BaseRepo
import com.raantech.mycups.data.models.promocode.PromoCode
import javax.inject.Inject

class CartRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val cartLocalDao: CartLocalDao
) : BaseRepo(responseHandler), CartRepo {

    override suspend fun saveCart(accessory: Product) {
        cartLocalDao.saveCart(accessory)
    }

    override suspend fun saveCarts(accessories: List<Product>) {
        cartLocalDao.saveCarts(accessories)
    }

    override suspend fun loadCarts(): List<Product> {
        return cartLocalDao.getCarts()
    }

    override suspend fun getCart(id: Int): Product {
        return cartLocalDao.getCarts(id)
    }

    override fun getCartsCount(): LiveData<Int> {
        return cartLocalDao.getCartsCount()
    }

    override suspend fun getCartsCountInt(): Int? {
        return cartLocalDao.getCartsCountInt()
    }

    override suspend fun deleteCart(id: Int) {
        cartLocalDao.deleteCart(id)
    }

    override suspend fun clearCart() {
        cartLocalDao.clearCart()
    }


}