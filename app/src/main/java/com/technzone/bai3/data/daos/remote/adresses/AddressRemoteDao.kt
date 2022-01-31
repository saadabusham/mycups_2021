package com.technzone.bai3.data.daos.remote.adresses

import androidx.room.FtsOptions
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.common.NetworkConstants
import com.technzone.bai3.data.models.FaqsResponse
import com.technzone.bai3.data.models.addresses.AddressList
import com.technzone.bai3.data.models.category.Category
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.home.banner.Banner
import com.technzone.bai3.data.models.home.product.ProductFilter
import com.technzone.bai3.data.models.home.product.productdetails.Product
import com.technzone.bai3.data.models.home.product.productdetails.SocialMedia
import com.technzone.bai3.data.models.notification.Notification
import com.technzone.bai3.data.models.orders.Order
import com.technzone.bai3.data.models.orders.OrderDetails
import okhttp3.MultipartBody
import okhttp3.RequestBody
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