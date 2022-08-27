package com.raantech.mycups.ui.notifications.viewmodel

import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val userRepo: UserRepo
) : BaseViewModel() {

    fun getNotifications(
        skip: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            userRepo.getNotifications(skip)
        emit(response)
    }

}