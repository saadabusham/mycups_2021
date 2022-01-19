package com.technzone.bai3.data.pref.configuration

interface ConfigurationPref {

    fun setAppLanguageValue(selectedLanguageValue: String)
    fun getAppLanguageValue():String
}