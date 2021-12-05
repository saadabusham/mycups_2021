package com.technzone.baseapp.ui.splash

import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.technzone.baseapp.data.api.response.APIResource
import com.technzone.baseapp.data.enums.UserEnums
import com.technzone.baseapp.data.models.auth.login.UserDetailsResponseModel
import com.technzone.baseapp.data.pref.configuration.ConfigurationPref
import com.technzone.baseapp.data.pref.user.UserPref
import com.technzone.baseapp.data.repos.auth.UserRepo
import com.technzone.baseapp.data.repos.configuration.ConfigurationRepo
import com.technzone.baseapp.ui.base.viewmodel.BaseViewModel
import com.technzone.baseapp.utils.LocaleUtil
import com.technzone.baseapp.utils.pref.SharedPreferencesUtil
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