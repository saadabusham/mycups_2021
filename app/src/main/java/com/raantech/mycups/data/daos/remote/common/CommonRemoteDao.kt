package com.raantech.mycups.data.daos.remote.common

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.FaqsResponse
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.banner.Banner
import com.raantech.mycups.data.models.home.product.productdetails.SocialMedia
import com.raantech.mycups.data.models.notification.Notification
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
    @GET("api/ContactUsSocialMedia")
    suspend fun getContactUsSocialMedia(
    ): ResponseWrapper<List<SocialMedia>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/deal")
    suspend fun getBanner(
        @Query("PageSize") pageSize: Int,
        @Query("PageNumber") pageNumber: Int
    ): ResponseWrapper<ListWrapper<Banner>>

}