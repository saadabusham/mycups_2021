package com.technzone.bai3.data.repos.address

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseHandler
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.daos.remote.adresses.AddressRemoteDao
import com.technzone.bai3.data.models.addresses.AddressList
import com.technzone.bai3.data.repos.base.BaseRepo
import com.technzone.bai3.data.repos.common.CommonRepo
import javax.inject.Inject

class AddressRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val addressRemoteDao: AddressRemoteDao
) : BaseRepo(responseHandler), AddressRepo {

    override suspend fun getMyAddress(): APIResource<ResponseWrapper<List<AddressList>>> {
        return try {
            responseHandle.handleSuccess(addressRemoteDao.getMyAddress())
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
                addressRemoteDao.addAddress(
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
                addressRemoteDao.updateAddress(
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
            responseHandle.handleSuccess(addressRemoteDao.deleteAddress(id))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }
}