package com.technzone.bai3.data.repos.common

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.FaqsResponse
import com.technzone.bai3.data.models.category.Category
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.home.banner.Banner
import com.technzone.bai3.data.models.home.product.productdetails.SocialMedia
import com.technzone.bai3.data.models.notification.Notification
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