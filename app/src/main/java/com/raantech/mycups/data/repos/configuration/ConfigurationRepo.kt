package com.raantech.mycups.data.repos.configuration

import com.raantech.mycups.common.CommonEnums
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.configuration.ConfigurationWrapperResponse
import com.raantech.mycups.data.models.more.AboutUsResponse

interface ConfigurationRepo {

    fun setAppLanguage(selectedLanguage: CommonEnums.Languages)
    fun getAppLanguage(): CommonEnums.Languages

    suspend fun loadConfigurationData(): APIResource<ResponseWrapper<ConfigurationWrapperResponse>>

    suspend fun getAboutUs(): APIResource<ResponseWrapper<AboutUsResponse>>
}