package com.raantech.mycups.data.daos.remote.configuration

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.configuration.ConfigurationWrapperResponse
import retrofit2.http.GET

interface ConfigurationRemoteDao {

    @GET("api/configuration")
    suspend fun getAppConfiguration(): ResponseWrapper<ConfigurationWrapperResponse>
}