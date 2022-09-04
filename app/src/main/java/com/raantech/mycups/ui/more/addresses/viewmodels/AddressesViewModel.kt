package com.raantech.mycups.ui.more.addresses.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.models.addresses.UserAddress
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

    var addressToEdit: UserAddress? = null
    val name: MutableLiveData<String?> = MutableLiveData("")
    val phone: MutableLiveData<String?> by lazy { MutableLiveData<String?>() }
    val city: MutableLiveData<String?> = MutableLiveData(null)
    val district: MutableLiveData<String?> = MutableLiveData("")
    val street: MutableLiveData<String?> = MutableLiveData("")
    val buildingNumber: MutableLiveData<String?> = MutableLiveData("")
    val description: MutableLiveData<String?> = MutableLiveData("")
    val latitude: MutableLiveData<Double?> = MutableLiveData()
    val longitude: MutableLiveData<Double?> = MutableLiveData()
    val addressStr: MutableLiveData<String> = MutableLiveData()

    fun addAddress() = liveData {
        emit(APIResource.loading())
        val response = userRepo.updateAddress(
            name = name.value.toString(),
            phone = phone.value.toString(),
            city = city.value.toString(),
            district = district.value.toString(),
            street = street.value.toString(),
            building_number = buildingNumber.value.toString(),
            description = description.value.toString(),
            latitude = latitude.value ?: 0.0,
            longitude = longitude.value ?: 0.0
        )
//        if (response.statusSubCode == ResponseSubErrorsCodeEnum.Success) {
//            val user = userRepo.getUser()
//            user?.userInfo = response.data?.body
//            user?.let { userRepo.setUser(it) }
//        }
        emit(response)
    }

    fun setData() {
//        userRepo.getUser()?.user.let {
//            addressToEdit = it?.address
//        }
        addressToEdit?.let {
            name.value = it.name
            phone.value = it.phone
            city.value = it.city
            district.value = it.district
            street.value = it.street
            buildingNumber.value = it.buildingNumber
            description.value = it.description
            latitude.value = it.latitude
            longitude.value = it.longitude
            addressStr.value = context.getLocationName(it.latitude, it.longitude)
        }
    }

}