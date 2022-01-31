package com.technzone.bai3.data.repos.address

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.addresses.AddressList


interface AddressRepo {

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
}