package com.raantech.mycups.data.daos.remote.media

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.media.Media
import okhttp3.MultipartBody
import retrofit2.http.*

interface MediaRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("media")
    suspend fun getMedia(
        @Query("mime_types") mime_types: String
    ): ResponseWrapper<List<Media>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @Multipart
    @POST("media/store")
    suspend fun uploadMedia(
        @Part mediaFile: MultipartBody.Part
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @DELETE("media/{mediaId}/destroy")
    suspend fun deleteMedia(
        @Path("mediaId") mediaId: Int
    ): ResponseWrapper<Any>


}