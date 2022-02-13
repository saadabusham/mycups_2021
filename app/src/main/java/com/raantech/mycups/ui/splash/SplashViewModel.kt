package com.raantech.mycups.ui.splash

import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.pref.configuration.ConfigurationPref
import com.raantech.mycups.data.pref.user.UserPref
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.data.repos.configuration.ConfigurationRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.LocaleUtil
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
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