package com.raantech.mycups.data.daos.remote.adresses

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.addresses.AddressList
import retrofit2.http.*

interface AddressRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/user/address")
    suspend fun getMyAddress(
    ): ResponseWrapper<List<AddressList>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @FormUrlEncoded
    @POST("api/user/address")
    suspend fun addAddress(
            @Field("Name") name: String,
            @Field("ContactName") contactName: String,
            @Field("PhoneNumber") phoneNumber: String,
            @Field("Longitude") longitude: Double?,
            @Field("Latitude") latitude: Double?,
            @Field("ZipCode") zipCode: String,
            @Field("AddressLine1") addressLine1: String,
            @Field("AddressLine2") addressLine2: String,
            @Field("City") city: String,
            @Field("CountryId") countryId: Int,
            @Field("IsDefault") IsDefault: Boolean,
            @Field("CountryCode") countryCode: String,
    ): ResponseWrapper<String>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @FormUrlEncoded
    @PATCH("api/user/address/{id}")
    suspend fun updateAddress(
            @Path("id") id: Int,
            @Field("Name") name: String,
            @Field("ContactName") contactName: String,
            @Field("PhoneNumber") phoneNumber: String,
            @Field("Longitude") longitude: Double?,
            @Field("Latitude") latitude: Double?,
            @Field("ZipCode") zipCode: String,
            @Field("AddressLine1") addressLine1: String,
            @Field("AddressLine2") addressLine2: String,
            @Field("City") city: String,
            @Field("CountryId") countryId: Int,
            @Field("IsDefault") isDefault: Boolean,
            @Field("CountryCode") countryCode: String,
    ): ResponseWrapper<String>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @DELETE("api/user/address/{id}")
    suspend fun deleteAddress(
            @Path("id") id: String
    ): ResponseWrapper<Any>
}