package com.raantech.mycups.ui.main.fragments.profile.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.raantech.mycups.BuildConfig
import com.raantech.mycups.common.CommonEnums
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.models.general.Countries
import com.raantech.mycups.data.pref.configuration.ConfigurationPref
import com.raantech.mycups.data.pref.user.UserPref
import com.raantech.mycups.data.repos.common.CommonRepo
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.LocaleUtil
import com.raantech.mycups.utils.extensions.checkPhoneNumberFormat
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    private val commonRepo: CommonRepo,
    private val configurationPref: ConfigurationPref,
    private val userPref: UserPref
) : BaseViewModel() {

    val phoneNumberWithoutCountryCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val selectedCountryCode: MutableLiveData<Countries> by lazy { MutableLiveData<Countries>() }
    val fullNameMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>("") }
    val emailMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val userImageMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val loggedIn : MutableLiveData<Boolean> = MutableLiveData()

    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn

    fun getUser() {
        userRepo.getUser()?.let {
            fullNameMutableLiveData.value = it.user?.name
            emailMutableLiveData.value = it.user?.email
            phoneNumberWithoutCountryCode.value = it.user?.phoneNumber?.checkPhoneNumberFormat()
            userImageMutableLiveData.value = it.user?.picture
        }
    }

    fun logout() = viewModelScope.launch {
        sharedPreferencesUtil.clearPreference()
        userPref.setIsFirstOpen(false)
        configurationPref.setAppLanguageValue(LocaleUtil.getLanguage())
    }

    fun getMyProfile() = liveData {
        emit(APIResource.loading())
        val response = userRepo.refreshToken(userRepo.getUser()?.accessToken ?: "")
        emit(response)
    }

    fun storeUser(user: UserDetailsResponseModel) {
        userRepo.setUser(user)
    }

    fun appVersion():String{
        return BuildConfig.VERSION_NAME
    }

    fun getShareText(): String {
        return if (configurationPref.getAppLanguageValue() == CommonEnums.Languages.English.value)
            sharedPreferencesUtil.getConfigurationPreferences()?.configString?.englishTellAFriend
                ?: ""
        else sharedPreferencesUtil.getConfigurationPreferences()?.configString?.arabicTellAFriend
            ?: ""
    }

    fun getTermsAndConditions(): String {
        return if (configurationPref.getAppLanguageValue() == CommonEnums.Languages.English.value)
            sharedPreferencesUtil.getConfigurationPreferences()?.configString?.englishTermsAndConditions
                ?: ""
        else sharedPreferencesUtil.getConfigurationPreferences()?.configString?.arabicTermsAndConditions
            ?: ""
    }


    fun getPrivacyPolicy(): String {
        return if (configurationPref.getAppLanguageValue() == CommonEnums.Languages.English.value)
            sharedPreferencesUtil.getConfigurationPreferences()?.configString?.englishPrivacyPolicy
                ?: ""
        else sharedPreferencesUtil.getConfigurationPreferences()?.configString?.arabicPrivacyPolicy
            ?: ""
    }
}