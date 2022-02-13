package com.raantech.mycups.data.daos.remote.lookups

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.general.Countries
import com.raantech.mycups.data.models.general.ListWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface LookupsRemoteDao {

    @GET("api/country")
    suspend fun getCountriesCode(
        @Query("pageSize") pageSize: Int,
        @Query("pageNumber") pageNumber: Int,
        @Query("Name") name: String? = "",
        @Query("Code") code: String? = ""
    ): ResponseWrapper<ListWrapper<Countries>>

}