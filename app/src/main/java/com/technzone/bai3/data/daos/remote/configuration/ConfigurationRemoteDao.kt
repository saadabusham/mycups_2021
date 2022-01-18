package com.technzone.bai3.data.daos.remote.configuration

import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.configuration.ConfigurationWrapperResponse
import retrofit2.http.GET

interface ConfigurationRemoteDao {

    @GET("api/configuration")
    suspend fun getAppConfiguration(): ResponseWrapper<ConfigurationWrapperResponse>
}