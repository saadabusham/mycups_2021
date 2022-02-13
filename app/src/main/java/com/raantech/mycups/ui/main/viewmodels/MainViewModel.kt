package com.raantech.mycups.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.enums.NavigationTabsEnum
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.pref.cart.CartPref
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.extensions.getRequestBody
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    private val cartPref: CartPref,
) : BaseViewModel() {
    val selectedTab:MutableLiveData<NavigationTabsEnum> = MutableLiveData(NavigationTabsEnum.HOME)
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