package com.technzone.bai3.data.daos.remote.lookups

import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.general.Countries
import com.technzone.bai3.data.models.general.ListWrapper
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