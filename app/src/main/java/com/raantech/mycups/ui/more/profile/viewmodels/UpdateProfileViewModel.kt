package com.raantech.mycups.ui.more.profile.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.models.auth.login.User
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    @ApplicationContext context: Context
) : BaseViewModel() {

    //    register
    val username: MutableLiveData<String> = MutableLiveData()
    val phoneNumber: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val address: MutableLiveData<String> = MutableLiveData()

    init {
        userRepo.getUser()?.user?.let {
            username.postValue(it.name)
            phoneNumber.postValue(it.phoneNumber)
            email.postValue(it.email)
//            address.postValue(it.address)
        }
    }
    fun updateUser() = liveData {
        emit(APIResource.loading())
        val response = userRepo.updateProfile(
            username.value.toString(),
            email.value.toString()
        )
        emit(response)
    }

    fun storeUser(userinfo: User) {
        val user = userRepo.getUser()
        user?.user = userinfo
        user?.let { userRepo.setUser(it) }
    }
}