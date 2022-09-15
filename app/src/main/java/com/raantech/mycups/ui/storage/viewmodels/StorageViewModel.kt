package com.raantech.mycups.ui.storage.viewmodels

import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.models.storage.StorageRequest
import com.raantech.mycups.data.models.storage.StorageResponse
import com.raantech.mycups.data.repos.storage.StorageRepo
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val storageRepo: StorageRepo
) : BaseViewModel() {

    fun getStorages(
    ) = liveData {
        emit(APIResource.loading())
        val response =
            storageRepo.getStorages()
        emit(response)
    }

    fun requestStorages(
        storageRequest: StorageRequest
    ) = liveData {
        emit(APIResource.loading())
        val response =
            storageRepo.requestStorages(storageRequest)
        emit(response)
    }

}