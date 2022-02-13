package com.raantech.mycups.ui.auth

import android.content.Context
import com.raantech.mycups.BuildConfig
import com.raantech.mycups.common.CommonEnums
import com.raantech.mycups.data.pref.configuration.ConfigurationPref
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepo: UserRepo,
    @ApplicationContext val context: Context,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    private val configurationPref: ConfigurationPref
) : BaseViewModel() {

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

    fun appVersion():String{
        return BuildConfig.VERSION_NAME
    }
}