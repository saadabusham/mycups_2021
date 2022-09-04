package com.raantech.mycups.data.repos.storage

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.remote.storage.StorageRemoteDao
import com.raantech.mycups.data.models.storage.StorageResponse
import com.raantech.mycups.data.repos.base.BaseRepo
import javax.inject.Inject

class StorageRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val storageRemoteDao: StorageRemoteDao
) : BaseRepo(responseHandler), StorageRepo {

    override suspend fun getStorages(): APIResource<ResponseWrapper<StorageResponse>> {
        return try {
            responseHandle.handleSuccess(storageRemoteDao.getStorages())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }
}