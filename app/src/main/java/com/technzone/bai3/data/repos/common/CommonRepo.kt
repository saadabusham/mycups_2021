package com.technzone.bai3.data.repos.common

import androidx.room.FtsOptions
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.FaqsResponse
import com.technzone.bai3.data.models.addresses.AddressList
import com.technzone.bai3.data.models.category.Category
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.home.product.ProductFilter
import com.technzone.bai3.data.models.home.product.productdetails.Product
import com.technzone.bai3.data.models.home.product.productdetails.SocialMedia
import com.technzone.bai3.data.models.notification.Notification
import com.technzone.bai3.data.models.orders.Order
import com.technzone.bai3.data.models.orders.OrderDetails
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface CommonRepo {

    suspend fun getNotifications(
        pageSize: Int,
        pageNumber: Int
    ): APIResource<ResponseWrapper<ListWrapper<Notification>>>

    suspend fun getFavorites(
        productFilter: ProductFilter
    ): APIResource<ResponseWrapper<ListWrapper<Product>>>

    suspend fun getFavoriteIds(
    ): APIResource<ResponseWrapper<List<Int>>>

    suspend fun addFavorite(
        id: Int
    ): APIResource<ResponseWrapper<Any>>

    suspend fun removeFavorite(
        id: Int
    ): APIResource<ResponseWrapper<Any>>

    suspend fun getFaqs(
        pageSize: Int,
        pageNumber: Int
    ): APIResource<ResponseWrapper<ListWrapper<FaqsResponse>>>

    suspend fun contactUs(
        message: RequestBody,
        image1: MultipartBody.Part? = null,
        image2: MultipartBody.Part? = null,
        image3: MultipartBody.Part? = null,
    ): APIResource<ResponseWrapper<Any>>

    suspend fun getCategories(
        pageSize: Int,
        pageNumber: Int,
        name: String? = null,
        parentId: Int? = null
    ): APIResource<ResponseWrapper<ListWrapper<Category>>>

    suspend fun getMyAddress(
    ): APIResource<ResponseWrapper<List<AddressList>>>

    suspend fun addAddress(
        name: String,
        contactName: String,
        phoneNumber: String,
        longitude: Double?,
        latitude: Double?,
        zipCode: String,
        addressLine1: String,
        addressLine2: String,
        city: String,
        countryId: Int,
        isDefault: Boolean,
        countryCode: String,
    ): APIResource<ResponseWrapper<String>>

    suspend fun updateAddress(
        id: Int,
        name: String,
        contactName: String,
        phoneNumber: String,
        longitude: Double?,
        latitude: Double?,
        zipCode: String,
        addressLine1: String,
        addressLine2: String,
        city: String,
        countryId: Int,
        isDefault: Boolean,
        countryCode: String
    ): APIResource<ResponseWrapper<String>>

    suspend fun deleteAddress(
        id: String
    ): APIResource<ResponseWrapper<Any>>

    suspend fun getOrders(
        pageSize: Int,
        pageNumber: Int,
        status: Int?
    ): APIResource<ResponseWrapper<ListWrapper<Order>>>

    suspend fun getOrderDetails(
        id: Int
    ): APIResource<ResponseWrapper<OrderDetails>>

    suspend fun getContactUsSocialMedia(
    ): APIResource<ResponseWrapper<List<SocialMedia>>>
}