package com.raantech.mycups.ui.more.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.pref.configuration.ConfigurationPref
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val sharedPreferencesUtil: SharedPreferencesUtil,
    val configurationPref: ConfigurationPref,
    val userRepo: UserRepo
) : BaseViewModel() {

    val oldPasswordMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val newPasswordMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val confirmPasswordMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun changePassword() = liveData {
        emit(APIResource.loading())
        val response = userRepo.updatePassword(
                oldPassword = oldPasswordMutableLiveData.value.toString(),
                newPassword = newPasswordMutableLiveData.value.toString()
        )
        if(response.statusSubCode == ResponseSubErrorsCodeEnum.Success)
            userRepo.saveUserPassword(newPasswordMutableLiveData.value?:"")
        emit(response)
    }

}