package com.raantech.mycups.data.repos.lookups

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.models.general.Countries
import com.raantech.mycups.data.models.general.ListWrapper

interface LookupsRepo {
    suspend fun getCountriesCode(
        pageSize: Int,
        pageNumber: Int,
        name: String? = "",
        code: String? = ""
    ): APIResource<ResponseWrapper<ListWrapper<Countries>>>
}