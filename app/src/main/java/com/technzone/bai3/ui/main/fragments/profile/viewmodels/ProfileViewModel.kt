package com.technzone.bai3.ui.main.fragments.profile.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.technzone.bai3.BuildConfig
import com.technzone.bai3.common.CommonEnums
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.enums.UserEnums
import com.technzone.bai3.data.models.auth.login.UserDetailsResponseModel
import com.technzone.bai3.data.models.general.Countries
import com.technzone.bai3.data.pref.configuration.ConfigurationPref
import com.technzone.bai3.data.pref.user.UserPref
import com.technzone.bai3.data.repos.common.CommonRepo
import com.technzone.bai3.data.repos.configuration.ConfigurationRepo
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import com.technzone.bai3.utils.LocaleUtil
import com.technzone.bai3.utils.extensions.checkPhoneNumberFormat
import com.technzone.bai3.utils.pref.SharedPreferencesUtil
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
            fullNameMutableLiveData.value = it.fullName
            emailMutableLiveData.value = it.email
            phoneNumberWithoutCountryCode.value = it.phoneNumber?.checkPhoneNumberFormat()
            userImageMutableLiveData.value = it.picture
        }
    }

    fun logout() = viewModelScope.launch {
        sharedPreferencesUtil.clearPreference()
        userPref.setIsFirstOpen(false)
        configurationPref.setAppLanguageValue(LocaleUtil.getLanguage())
    }

    fun getMyProfile() = liveData {
        emit(APIResource.loading())
        val response = userRepo.refreshToken(userRepo.getUser()?.refreshToken?.refreshToken ?: "")
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