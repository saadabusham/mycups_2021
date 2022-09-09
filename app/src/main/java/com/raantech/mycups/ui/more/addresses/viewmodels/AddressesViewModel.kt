package com.raantech.mycups.ui.more.addresses.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.models.auth.login.Address
import com.raantech.mycups.data.models.auth.login.User
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.getLocationName
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val userRepo: UserRepo,
    @ApplicationContext private val context: Context
) : BaseViewModel() {

    var addressToEdit: Address? = null
    val addressName: MutableLiveData<String?> = MutableLiveData("")
    val latitude: MutableLiveData<Double?> = MutableLiveData()
    val longitude: MutableLiveData<Double?> = MutableLiveData()
    val addressStr: MutableLiveData<String> = MutableLiveData()

    fun addAddress() = liveData {
        emit(APIResource.loading())
        val response = userRepo.updateAddress(
            name = getUserData()?.name?:"",
            hasStock = if(getUserData()?.hasStock == true) 1 else 0,
            latitude = latitude.value ?: 0.0,
            longitude = longitude.value ?: 0.0,
            addressText = addressName.value.toString()
        )
        if (response.statusSubCode == ResponseSubErrorsCodeEnum.Success) {
            val user = userRepo.getUser()
            user?.user = response.data?.body?.user
            user?.let { userRepo.setUser(it) }
        }
        emit(response)
    }

    fun setData() {
        userRepo.getUser()?.user.let {
            addressToEdit = it?.address
        }
        addressToEdit?.let {
            addressName.value = it.addressText
            latitude.value = it.addressLat
            longitude.value = it.addressLng
            addressStr.value = context.getLocationName(it.addressLat, it.addressLng)
        }
    }
    fun getUserData():User?{
        return userRepo.getUser()?.user
    }
}