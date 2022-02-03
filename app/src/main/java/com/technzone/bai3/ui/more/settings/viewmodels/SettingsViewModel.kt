package com.technzone.bai3.ui.more.settings.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import co.infinum.goldfinger.Goldfinger
import com.technzone.bai3.R
import com.technzone.bai3.common.CommonEnums
import com.technzone.bai3.data.enums.UserEnums
import com.technzone.bai3.data.pref.configuration.ConfigurationPref
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val configurationPref: ConfigurationPref,
    val userRepo: UserRepo,
    @ApplicationContext val context: Context
) : BaseViewModel() {

    val englishSelected: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(true) }

    fun getIsNotificationsIsEnabled(): Boolean? {
        return userRepo.getNotificationStatus()
    }

    fun setIsNotificationsIsEnabled(enabled: Boolean) {
        return userRepo.saveNotificationStatus(enabled)
    }

    fun getAppLanguage(): String? {
        return if (configurationPref.getAppLanguageValue() == CommonEnums.Languages.English.value) {
            englishSelected.postValue(true)
            context.resources.getString(R.string.english)
        } else {
            englishSelected.postValue(false)
            context.resources.getString(R.string.arabic)
        }
    }

    fun saveLanguage(isEnglishSelected:Boolean) = liveData {
        configurationPref.setAppLanguageValue(if (isEnglishSelected)
            CommonEnums.Languages.English.value
        else CommonEnums.Languages.Arabic.value)
        englishSelected.value = isEnglishSelected
        emit(null)
    }

    fun getIsTouchIDEnabled(): Boolean? {
        return userRepo.getTouchIdStatus()
    }

    fun setIsTouchIDEnabled(enabled: Boolean) {
        return userRepo.saveTouchIdStatus(enabled)
    }

    fun isTouchIdShouldVisible():Int{
        return if(userRepo.getUserStatus() == UserEnums.UserState.LoggedIn &&
                Goldfinger.Builder(context).build().canAuthenticate())
            View.VISIBLE
        else View.GONE
    }

//    fun updateNotificationStatus(enabled: Boolean) = liveData {
//        emit(APIResource.loading())
//        val response = userRepo.updateNotificationStatus(enabled.toString().getRequestBody())
//        emit(response)
//    }
    fun storeUser(enabled: Boolean) {
        val user = userRepo.getUser()
//        user?.notificationEnabled = enabled
        setIsNotificationsIsEnabled(enabled)
        if (user != null) {
            userRepo.setUser(user)
        }
    }
    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn
}