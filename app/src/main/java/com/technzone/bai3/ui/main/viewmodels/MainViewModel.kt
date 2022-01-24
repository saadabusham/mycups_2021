package com.technzone.bai3.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.data.enums.UserEnums
import com.technzone.bai3.data.pref.cart.CartPref
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import com.technzone.bai3.utils.extensions.getRequestBody
import com.technzone.bai3.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    private val cartPref: CartPref,
) : BaseViewModel() {

    val cartCount: MutableLiveData<Int> = MutableLiveData(0)
    fun isNewNotification(): Boolean {
        return sharedPreferencesUtil.getIsNewNotifications()
    }

    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn

    fun getCartsCount() = viewModelScope.launch {
        cartCount.postValue(cartPref.getCartList().count())
    }

    fun updateRegId(token: String) = liveData {
        emit(APIResource.loading())
        val response = userRepo.updateFcmToken(
            registrationId = token.getRequestBody(),
            deviceType = Constants.APPLICATION_TYPE.toString().getRequestBody()
        )
        emit(response)
    }


}