package com.technzone.bai3.data.pref.configuration

import com.technzone.bai3.common.CommonEnums
import com.technzone.bai3.utils.pref.PrefConstants.APP_LANGUAGE_VALUE
import com.technzone.bai3.utils.pref.SharedPreferencesUtil
import javax.inject.Inject

class ConfigurationPrefImp @Inject constructor(private val prefUtil: SharedPreferencesUtil) :
    ConfigurationPref {

    override fun setAppLanguageValue(selectedLanguageValue: String) {
        prefUtil.setStringPreferences(APP_LANGUAGE_VALUE, selectedLanguageValue)
    }

    override fun getAppLanguageValue(): String {
        return prefUtil.getStringPreferences(
            APP_LANGUAGE_VALUE,
           CommonEnums.Languages.English.value
        )
    }
}