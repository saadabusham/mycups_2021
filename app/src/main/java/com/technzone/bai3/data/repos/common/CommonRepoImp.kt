package com.technzone.bai3.data.repos.common

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseHandler
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.daos.remote.common.CommonRemoteDao
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
import com.technzone.bai3.data.repos.base.BaseRepo
import com.technzone.bai3.data.repos.common.CommonRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class CommonRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val commonRemoteDao: CommonRemoteDao
) : BaseRepo(responseHandler), CommonRepo {

    override suspend fun getNotifications(
        pageSize: Int,
        pageNumber: Int
    ): APIResource<ResponseWrapper<ListWrapper<Notification>>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.getNotifications(pageSize, pageNumber)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getFavorites(
        productFilter: ProductFilter
    ): APIResource<ResponseWrapper<ListWrapper<Product>>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.getFavorites(
                    productFilter
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getFavoriteIds(): APIResource<ResponseWrapper<List<Int>>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.getFavoriteIds()
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun addFavorite(id: Int): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.addFavorite(id)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun removeFavorite(id: Int): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.removeFavorite(id)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getFaqs(
        pageSize: Int,
        pageNumber: Int
    ): APIResource<ResponseWrapper<ListWrapper<FaqsResponse>>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.getFaqs(pageSize, pageNumber))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun contactUs(
        message: RequestBody,
        image1: MultipartBody.Part?,
        image2: MultipartBody.Part?,
        image3: MultipartBody.Part?
    ): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.contactUs(message, image1, image2, image3))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getCategories(
        pageSize: Int,
        pageNumber: Int,
        name: String?,
        parentId: Int?
    ): APIResource<ResponseWrapper<ListWrapper<Category>>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.getCategories(
                    pageSize,
                    pageNumber,
                    name,
                    parentId
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getMyAddress(): APIResource<ResponseWrapper<List<AddressList>>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.getMyAddress())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun addAddress(
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
    ): APIResource<ResponseWrapper<String>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.addAddress(
                    name,
                    contactName,
                    phoneNumber,
                    longitude,
                    latitude,
                    zipCode,
                    addressLine1,
                    addressLine2,
                    city,
                    countryId,
                    isDefault,
                    countryCode
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun updateAddress(
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
    ): APIResource<ResponseWrapper<String>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.updateAddress(
                    id,
                    name,
                    contactName,
                    phoneNumber,
                    longitude,
                    latitude,
                    zipCode,
                    addressLine1,
                    addressLine2,
                    city,
                    countryId,
                    isDefault,
                    countryCode
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun deleteAddress(id: String): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.deleteAddress(id))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getOrders(
        pageSize: Int,
        pageNumber: Int,
        status: Int?
    ): APIResource<ResponseWrapper<ListWrapper<Order>>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.getOrders(pageSize, pageNumber, status))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getOrderDetails(id: Int): APIResource<ResponseWrapper<OrderDetails>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.getOrderDetails(id))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getContactUsSocialMedia(): APIResource<ResponseWrapper<List<SocialMedia>>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.getContactUsSocialMedia())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}