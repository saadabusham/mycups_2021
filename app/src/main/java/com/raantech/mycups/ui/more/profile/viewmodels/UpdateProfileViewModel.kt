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
    val username: MutableLiveData<String?> = MutableLiveData()
    val phoneNumber: MutableLiveData<String?> = MutableLiveData()
    val email: MutableLiveData<String?> = MutableLiveData()
    val address: MutableLiveData<String?> = MutableLiveData()
    val brandName: MutableLiveData<String?> = MutableLiveData()
    val hasStock: MutableLiveData<Boolean?> = MutableLiveData()

    init {
        userRepo.getUser()?.user?.let {
            username.postValue(it.name)
            phoneNumber.postValue(it.phoneNumber)
            email.postValue(it.email)
            brandName.postValue(it.brandName)
            address.postValue(it.address?.addressText)
            hasStock.postValue(it.hasStock)
        }
    }

    fun updateUser() = liveData {
        emit(APIResource.loading())
        val response = userRepo.updateProfile(
            name = username.value.toString(),
            email = email.value.toString(),
            brandName = brandName.value.toString(),
            hasStock = if(hasStock.value == true) 1 else 0,
            latitude = getUserData()?.address?.addressLat ?: 0.0,
            longitude = getUserData()?.address?.addressLng ?: 0.0,
            addressText = getUserData()?.address?.addressText?:""
        )
        emit(response)
    }

    fun storeUser(userinfo: User) {
        val user = userRepo.getUser()
        user?.user = userinfo
        user?.let { userRepo.setUser(it) }
    }

    fun getUserData():User?{
        return userRepo.getUser()?.user
    }
}