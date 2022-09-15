package com.raantech.mycups.data.daos.remote.storage

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.storage.StorageRequest
import com.raantech.mycups.data.models.storage.StorageResponse
import retrofit2.http.*

interface StorageRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("stock")
    suspend fun getStorages(
    ): ResponseWrapper<StorageResponse>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("stock/order")
    suspend fun requestStorages(
        @Body storageRequest: StorageRequest
    ): ResponseWrapper<Any>

}