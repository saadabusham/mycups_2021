package com.raantech.mycups.data.repos.media

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.media.Media
import okhttp3.MultipartBody

interface MediaRepo {
    suspend fun getMedia(
        mime_types: String
    ): APIResource<ResponseWrapper<List<Media>>>

    suspend fun uploadMedia(
        mediaFile : MultipartBody.Part
    ): APIResource<ResponseWrapper<Any>>

    suspend fun deleteMedia(
        mediaId: Int
    ): APIResource<ResponseWrapper<Any>>
}