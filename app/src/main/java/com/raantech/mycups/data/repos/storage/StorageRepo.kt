package com.raantech.mycups.data.repos.storage

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.storage.StorageResponse


interface StorageRepo {

    suspend fun getStorages(
    ): APIResource<ResponseWrapper<StorageResponse>>

}