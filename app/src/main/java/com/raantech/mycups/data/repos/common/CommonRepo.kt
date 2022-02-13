package com.raantech.mycups.data.repos.common

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.FaqsResponse
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.banner.Banner
import com.raantech.mycups.data.models.home.product.productdetails.SocialMedia
import com.raantech.mycups.data.models.notification.Notification
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface CommonRepo {

    suspend fun getNotifications(
        pageSize: Int,
        pageNumber: Int
    ): APIResource<ResponseWrapper<ListWrapper<Notification>>>

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

    suspend fun getContactUsSocialMedia(
    ): APIResource<ResponseWrapper<List<SocialMedia>>>

    suspend fun getBanner(
        pageSize: Int,
        pageNumber: Int
    ): APIResource<ResponseWrapper<ListWrapper<Banner>>>

}