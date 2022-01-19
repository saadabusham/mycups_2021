package com.technzone.bai3.ui.splash

import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.enums.UserEnums
import com.technzone.bai3.data.models.auth.login.UserDetailsResponseModel
import com.technzone.bai3.data.pref.configuration.ConfigurationPref
import com.technzone.bai3.data.pref.user.UserPref
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.data.repos.configuration.ConfigurationRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import com.technzone.bai3.utils.LocaleUtil
import com.technzone.bai3.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val configurationRepo: ConfigurationRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    private val userPref: UserPref,
    private val configurationPref: ConfigurationPref
) : BaseViewModel() {

    fun getConfigurationData() = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.loadConfigurationData()
        emit(response)
    }

    fun updateAccessToken() = liveData {
        emit(APIResource.loading())
        val response = userRepo.refreshToken(userRepo.getUser()?.refreshToken?.refreshToken ?: "")
        emit(response)
    }

    fun logout() = viewModelScope.launch {
        sharedPreferencesUtil.clearPreference()
        userPref.setIsFirstOpen(false)
        configurationPref.setAppLanguageValue(LocaleUtil.getLanguage())
    }

    fun storeUser(user: UserDetailsResponseModel) {
        userRepo.setUser(user)
    }
    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn
}