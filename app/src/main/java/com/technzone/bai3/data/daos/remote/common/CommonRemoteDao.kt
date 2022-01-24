package com.technzone.bai3.data.daos.remote.common

import androidx.room.FtsOptions
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.common.NetworkConstants
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
import retrofit2.http.*

interface CommonRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/user/notification")
    suspend fun getNotifications(
            @Query("PageSize") pageSize: Int,
            @Query("PageNumber") pageNumber: Int
    ): ResponseWrapper<ListWrapper<Notification>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("api/product/search")
    suspend fun getFavorites(
            @Body productFilter: ProductFilter
    ): ResponseWrapper<ListWrapper<Product>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/user/product/favorite")
    suspend fun getFavoriteIds(
    ): ResponseWrapper<List<Int>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("api/user/product/favorite/{id}")
    suspend fun addFavorite(
            @Path("id") id: Int
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @DELETE("api/user/product/favorite/{id}")
    suspend fun removeFavorite(
            @Path("id") id: Int
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @GET("api/faq")
    suspend fun getFaqs(
            @Query("PageSize") pageSize: Int,
            @Query("PageNumber") pageNumber: Int
    ): ResponseWrapper<ListWrapper<FaqsResponse>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @Multipart
    @POST("api/contact-us")
    suspend fun contactUs(
            @Part("message") message: RequestBody,
            @Part image1: MultipartBody.Part?,
            @Part image2: MultipartBody.Part?,
            @Part image3: MultipartBody.Part?,
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/category")
    suspend fun getCategories(
            @Query("PageSize") pageSize: Int,
            @Query("PageNumber") pageNumber: Int,
            @Query("Name") name: String?,
            @Query("ParentId") parentId: Int?
    ): ResponseWrapper<ListWrapper<Category>>

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

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/order")
    suspend fun getOrders(
            @Query("PageSize") pageSize: Int,
            @Query("PageNumber") pageNumber: Int,
            @Query("Status") status: Int?
    ): ResponseWrapper<ListWrapper<Order>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/order/{id}")
    suspend fun getOrderDetails(
            @Path("id") id: Int
    ): ResponseWrapper<OrderDetails>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/ContactUsSocialMedia")
    suspend fun getContactUsSocialMedia(
    ): ResponseWrapper<List<SocialMedia>>

}