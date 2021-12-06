package com.technzone.baseapp.data.repos.lookups

import com.technzone.baseapp.data.api.response.APIResource
import com.technzone.baseapp.data.api.response.ResponseWrapper
import com.technzone.baseapp.data.models.general.Countries
import com.technzone.baseapp.data.models.general.ListWrapper

interface LookupsRepo {
    suspend fun getCountriesCode(
        pageSize: Int,
        pageNumber: Int,
        name: String? = "",
        code: String? = ""
    ): APIResource<ResponseWrapper<ListWrapper<Countries>>>
}